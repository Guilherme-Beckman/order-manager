package com.ms.auth.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ms.auth.dto.AuthenticationDTO;
import com.ms.auth.dto.UserDTO;
import com.ms.auth.dto.UserDetailsDTO;
import com.ms.auth.exceptions.auth.user.UserDataAlreadyExistsException;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.rabbitMQ.producer.UserServiceRegisterProducer;
import com.ms.auth.utils.MaxAttemptManager;
import com.ms.auth.utils.MessageUtils;

@Service
public class UserAuthenticationService {
	private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private UserServiceRegisterProducer registerRequestor;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MessageUtils messageUtils;
	@Autowired
	private MaxAttemptManager maxAttemptManager;

	public UserDetails registerUser(UserDTO userDTO) {

		if (this.customUserDetailsService.loadUserByUsername(userDTO.email()) != null) {
			throw new UserDataAlreadyExistsException("Email is already registered");
		}

		CompletableFuture<Message> responseFuture = new CompletableFuture<>();
		String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
		UserDTO newUser = new UserDTO(userDTO.cpf(), encryptedPassword, userDTO.name(), userDTO.lastName(),
				userDTO.email(), userDTO.address());
		String correlationId = this.messageUtils.generateCorrelationId();
		pendingResponses.put(correlationId, responseFuture);
		Message message = this.messageUtils.createMessage(newUser, correlationId);
		registerRequestor.requestRegister(message);
		try {
			Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
			return messageUtils.convertMessageToUserDetails(response);
		} catch (Exception e) {
			return null;
		} finally {
			pendingResponses.remove(correlationId);
		}
	}

	public String userLogin(AuthenticationDTO data) {
		try {
			this.maxAttemptManager.checkAndUpdateAttempts(data.login());
		} catch (Exception e) {
			throw e;
		}
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var user = (UserDetailsDTO) auth.getPrincipal();
		var token = tokenService.generateToken(data.login(), user.isValid());
		return token;
	}

	public void receiveResponse(Message message) {
		String responseCorrelationId = (String) message.getMessageProperties().getCorrelationId();
		CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
		if (responseFuture != null) {
			responseFuture.complete(message);
		}
	}



}
