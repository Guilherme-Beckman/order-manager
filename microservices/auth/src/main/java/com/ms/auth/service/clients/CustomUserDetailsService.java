package com.ms.auth.service.clients;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.auth.rabbitMQ.producer.clients.UserCredentialsProducer;
import com.ms.auth.utils.MessageUtils;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	public UserCredentialsProducer credentialsRequestor;
	@Autowired
	private MessageUtils messageUtils;
	private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String correlationId = this.messageUtils.generateCorrelationId();

		CompletableFuture<Message> responseFuture = new CompletableFuture<>();
		pendingResponses.put(correlationId, responseFuture);
		Message message = this.messageUtils.createMessage(email, correlationId);

		credentialsRequestor.requestUserCredentials(message);
		try {
			Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
			System.out.println("esta retornando de boas");
			return messageUtils.convertMessageToUserDetails(response);
		} catch (Exception e) {
			System.out.println("esta retornando null");
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
}
