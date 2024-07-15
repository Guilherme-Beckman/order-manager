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

import com.ms.auth.rabbitMQ.producer.store.StoreCredentialsProducer;
import com.ms.auth.utils.MessageUtils;

@Service
public class CustomStoreDetailsService implements UserDetailsService {
	@Autowired
	private StoreCredentialsProducer credentialsRequestor;
	@Autowired
	private MessageUtils messageUtils;
	private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String correlationId = this.messageUtils.generateCorrelationId();

		CompletableFuture<Message> responseFuture = new CompletableFuture<>();
		pendingResponses.put(correlationId, responseFuture);
		Message message = this.messageUtils.createMessage(email, correlationId);
		System.out.println("ta mandando a mesnsagem");
		credentialsRequestor.requestStoreCredentials(message);
		try {
			Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
			System.out.println("ta retorando taqnuilo");
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
			System.out.println("ta aqui recebdno a mensagem");
			responseFuture.complete(message);
		}
	}
}
