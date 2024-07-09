package com.ms.auth.service.clients;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.auth.exceptions.auth.authenticate.InvalideCodeException;
import com.ms.auth.exceptions.auth.user.UserNotFoundException;
import com.ms.auth.rabbitMQ.producer.EmailResetLinkUserEmailProducer;
import com.ms.auth.rabbitMQ.producer.EmailResetPasswordLinkProducer;
import com.ms.auth.utils.AttemptManagerExponencial;
import com.ms.auth.utils.MaxAttemptManager;
import com.ms.auth.utils.MessageUtils;

@Service
public class ResetPasswordService {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private AttemptManagerExponencial attemptManagerExponential;
	@Autowired
	private MaxAttemptManager maxAttemptManager;
	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private EmailResetPasswordLinkProducer emailResetPasswordLinkProducer;

	private ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();

	private String code = null;
	@Autowired
	private EmailResetLinkUserEmailProducer emailResetLinkUserEmailProducer;

	public void inviteResetPasswordEmail(String email) {
		email = email.replace("\"", "").trim();
		if (this.customUserDetailsService.loadUserByUsername(email) == null) {
			throw new UserNotFoundException("Email not found: " + email);
		}
		this.maxAttemptManager.checkAndUpdateAttempts(email, 3, 60);
		this.attemptManagerExponential.checkAndUpdateAttempts(email, 60 /* minutes */);
		CompletableFuture<String> responseFuture = new CompletableFuture<>();
		String code = registerResponseFutureCode(email, responseFuture);
		sendMessage(email, code);
		try {
		 responseFuture.get(60, TimeUnit.MINUTES);
		} catch (Exception e) {
		} finally {
			pendingResponses.remove(email + code);
		}

	}

	private String registerResponseFutureCode(String email, CompletableFuture<String> responseFuture) {
		code = UUID.randomUUID().toString();
		pendingResponses.put(email, responseFuture);
		return code;
	}

	private void sendMessage(String email, String code) {
		var message = messageUtils.createMessage(email, code);
		this.emailResetPasswordLinkProducer.produceResetPasswordLink(message);
	}

	public String validateEmailCode(String email, String token, String newPassword) {
		try {
			this.maxAttemptManager.checkAndUpdateAttempts(email, 3, 60);
		} catch (Exception e) {
			pendingResponses.remove(email);
			throw e;
		}
		if (!(token.equals(code))) throw new InvalideCodeException();
		
		CompletableFuture<String> responseFuture = pendingResponses.get(email);

		if (responseFuture != null) {
			newPassword = newPassword.replace("\"", "").trim();
			newPassword = new BCryptPasswordEncoder().encode(newPassword);
			responseFuture.complete(token);
			this.emailResetLinkUserEmailProducer.produceResetLinkUserEmail(email, newPassword);
			return "Password changed with sucess";
		}

		throw new InvalideCodeException();
	}
}
