package com.ms.auth.service.store;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.auth.rabbitMQ.producer.UserCredentialsProducer;
import com.ms.auth.utils.MessageUtils;

@Service
public class CustomStoreDetailsService implements UserDetailsService {
	@Autowired
	public UserCredentialsProducer credentialsRequestor;
	@Autowired
	MessageUtils messageUtils;
	private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String correlationId = this.messageUtils.generateCorrelationId();

		CompletableFuture<Message> responseFuture = new CompletableFuture<>();
		pendingResponses.put(correlationId, responseFuture);
		Message message = this.messageUtils.createMessage(email, correlationId);

		credentialsRequestor.requestUserCredentials(message);
		try {
			Message response = responseFuture.get(3500, TimeUnit.MILLISECONDS);
			return messageUtils.convertMessageToUserDetails(response);
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
}
