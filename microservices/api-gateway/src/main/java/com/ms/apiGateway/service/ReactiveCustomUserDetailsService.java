package com.ms.apiGateway.service;

import java.util.concurrent.CompletableFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ms.apiGateway.rabbitMQ.producer.UserCredentialsProducerApiGateway;
import com.ms.apiGateway.utils.MessageUtils;

import reactor.core.publisher.Mono;

@Component
public class ReactiveCustomUserDetailsService {
	@Autowired
	private UserCredentialsProducerApiGateway credentialsRequestor;
	@Autowired
	private MessageUtils messageUtils;
	private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

	public Mono<UserDetails> loadUserByUsername(String email) {
		return Mono.create(sink -> {
			String correlationId = this.messageUtils.generateCorrelationId();

			CompletableFuture<Message> responseFuture = new CompletableFuture<>();
			pendingResponses.put(correlationId, responseFuture);
			Message message = this.messageUtils.createMessage(email, correlationId);

			credentialsRequestor.requestUserCredentials(message);

			responseFuture.orTimeout(1000, TimeUnit.MILLISECONDS).whenComplete((response, error) -> {
				pendingResponses.remove(correlationId);
				if (error != null) {
					sink.error(error);
				} else {
					try {
						UserDetails userDetails = messageUtils.convertMessageToUserDetails(response);
						sink.success(userDetails);
					} catch (Exception e) {
						sink.error(e);
					}
				}
			});
		});

	}


	public void receiveResponse(Message message) {
		String responseCorrelationId = (String) message.getMessageProperties().getCorrelationId();

		CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
		if (responseFuture != null) {
			responseFuture.complete(message);
		}
	}
}