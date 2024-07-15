package com.ms.auth.service.store;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.auth.dto.clients.AuthenticationDTO;
import com.ms.auth.dto.clients.UserDetailsDTO;
import com.ms.auth.dto.store.StoreDTO;
import com.ms.auth.dto.store.StoreDetailsDTO;
import com.ms.auth.exceptions.auth.user.UserDataAlreadyExistsException;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.infra.security.customAuthentication.service.AuthenticationService;
import com.ms.auth.rabbitMQ.producer.store.StoreServiceRegisterProducer;
import com.ms.auth.service.TypeOfUser;
import com.ms.auth.utils.MaxAttemptManager;
import com.ms.auth.utils.MessageUtils;

import jakarta.validation.Valid;

@Service
public class StoreAuthenticationService {
	private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

	@Autowired
	private CustomStoreDetailsService customStoreDetailsService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private StoreServiceRegisterProducer registerRequestor;
	@Autowired
	private MaxAttemptManager maxAttemptManager;
	@Autowired
	private MessageUtils messageUtils;
	
	@Autowired
	private TokenService tokenService;

	public StoreDetailsDTO registerStore(StoreDTO data) {
		if (this.customStoreDetailsService.loadUserByUsername(data.email()) != null) {
			throw new UserDataAlreadyExistsException("Email is already registered");
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		StoreDTO newStore = new StoreDTO(data.name(), data.email(), encryptedPassword, data.address(), data.phone(), data.CNPJ(), data.opening_hours());
		CompletableFuture<Message> responseFuture = new CompletableFuture<>();
		String correlationId = this.messageUtils.generateCorrelationId();
		pendingResponses.put(correlationId, responseFuture);
		Message message = this.messageUtils.createMessage(newStore, correlationId);
		registerRequestor.requestRegister(message);

		try {
			Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
			return messageUtils.convertMessageToStoreDetails(response);
		} catch (Exception e) {
			return null;
		} finally {
			pendingResponses.remove(correlationId);
		}
	}

	public void receiveResponse(Message message) {
		String responseCorrelationId = (String) message.getMessageProperties().getCorrelationId();
		CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
		if (responseFuture != null) {
			responseFuture.complete(message);
		}
	}
	
	public String storeLogin(AuthenticationDTO data) {
		try {
			this.maxAttemptManager.checkAndUpdateAttempts(data.login());
		} catch (Exception e) {
			throw e;
		}
		//var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		//var auth = this.authenticationManager.authenticate(usernamePassword);
		this.authenticationService.authenticateStore(data.login(), data.password());
		var token = tokenService.generateToken(data.login(), TypeOfUser.STORE);
		return token;
	}
}
