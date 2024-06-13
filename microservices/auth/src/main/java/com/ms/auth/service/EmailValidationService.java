package com.ms.auth.service;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.auth.dto.ValidateEmailDTO;
import com.ms.auth.exceptions.auth.email.code.EmailAlreadyBeenVerifiedException;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.rabbitMQ.producer.EmailCodeProducer;
import com.ms.auth.rabbitMQ.producer.ValidateUserEmailProducer;
import com.ms.auth.utils.AttemptManagerExponencial;
import com.ms.auth.utils.MaxAttemptManager;
import com.ms.auth.utils.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailValidationService {
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
	private ValidateUserEmailProducer emailValidateUserEmailProducer;
	private String code;

	public String sendCode(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		Boolean isEnable = userInfos.getClaim("enable").asBoolean();
		String email = userInfos.getSubject();
		if (isEnable) {
			throw new EmailAlreadyBeenVerifiedException();
		}
		this.attemptManagerExponencial.checkAndUpdateAttempts(email);
		code = this.generateCode();
		System.out.println(code);
		String tokenEmailCode = this.tokenEmailCode(email, code);
		CompletableFuture<String> responseFuture = new CompletableFuture<>();
		pendingResponses.put(tokenEmailCode, responseFuture);
		Message message = this.messageUtils.createMessage(email, code);
		this.emailCodeProducer.produceEmailCode(message);

		try {
			responseFuture.get(60000, TimeUnit.MILLISECONDS);

			return "Email validated with success";
		} catch (Exception e) {
			return null;
		} finally {
			pendingResponses.remove(code);
		}
	}

	private String generateCode() {
		SecureRandom secureRandom = new SecureRandom();
		int code = secureRandom.nextInt(900000) + 100000;
		return String.valueOf(code);
	}

	private String tokenEmailCode(String email, String code) {
		String token = email + code;
		return token;
	}

	public String validateEmailCode(ValidateEmailDTO emailCodeDTO, HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);

		var userInfos = this.tokenService.getTokenInformations(token);
		String email = userInfos.getSubject();
		Boolean isEnable = userInfos.getClaim("enable").asBoolean();
		try {
			this.maxAttemptManager.checkAndUpdateAttempts(email);
		} catch (Exception e) {
			pendingResponses.remove(code);
			throw e;
		}

		if (isEnable) {
			throw new EmailAlreadyBeenVerifiedException();
		}
		String tokenEmailCode = this.tokenEmailCode(email, emailCodeDTO.emailCode());
		CompletableFuture<String> responseFuture = pendingResponses.get(tokenEmailCode);
		if (pendingResponses != null) {
			responseFuture.complete(emailCodeDTO.emailCode());
			this.emailValidateUserEmailProducer.produceValidateUserEmail(email);
			return "Email validated successfully";
		}

		return "";
	}
}
