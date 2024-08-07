package com.ms.auth.service.clients;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.auth.dto.clients.ValidateEmailDTO;
import com.ms.auth.exceptions.auth.authenticate.InvalideCodeException;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.rabbitMQ.producer.EmailCodeProducer;
import com.ms.auth.rabbitMQ.producer.clients.UserValidateEmailProducer;
import com.ms.auth.utils.AttemptManagerExponencial;
import com.ms.auth.utils.MaxAttemptManager;
import com.ms.auth.utils.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailService {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MessageUtils messageUtils;
	private ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();
	@Autowired
	private EmailCodeProducer emailCodeProducer;
	@Autowired
	private AttemptManagerExponencial attemptManagerExponencial;
	@Autowired
	private MaxAttemptManager maxAttemptManager;
	@Autowired
	private UserValidateEmailProducer emailValidateUserEmailProducer;
	private String code;

	public void sendCode(String token) {

		var userInfos = this.tokenService.getTokenInformations(token);
		String email = userInfos.getSubject();

		this.attemptManagerExponencial.checkAndUpdateAttempts(email);
		this.maxAttemptManager.checkAndUpdateAttempts(email);
		code = this.generateCode();

		CompletableFuture<String> responseFuture = new CompletableFuture<>();
		pendingResponses.put(email, responseFuture);

		Message message = this.messageUtils.createMessage(email, code);

		this.emailCodeProducer.produceEmailCode(message);

		try {
			responseFuture.get(60000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
		} finally {
			pendingResponses.remove(email);
		}
	}

	private String generateCode() {
		SecureRandom secureRandom = new SecureRandom();
		int code = secureRandom.nextInt(900000) + 100000;
		return String.valueOf(code);
	}

	public String validateEmailCode(ValidateEmailDTO emailCodeDTO, HttpServletRequest request) {

		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		String email = userInfos.getSubject();

		try {
			this.maxAttemptManager.checkAndUpdateAttempts(email);
		} catch (Exception e) {
			pendingResponses.remove(emailCodeDTO.emailCode());
			throw e;
		}

		if (!(emailCodeDTO.emailCode().equals(code)))
			throw new InvalideCodeException();

		CompletableFuture<String> responseFuture = pendingResponses.get(email);

		if (responseFuture != null) {
			responseFuture.complete("");
			this.emailValidateUserEmailProducer.produceValidateUserEmail(email);
			return "Email validated successfully";
		}

		return "Incorrect Code";
	}

}
