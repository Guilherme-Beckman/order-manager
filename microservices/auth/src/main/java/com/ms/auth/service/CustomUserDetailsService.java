package com.ms.auth.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.auth.dto.UserDetailsDTO;
import com.ms.auth.infra.security.CryptoUtils;
import com.ms.auth.rabbitMQ.producer.UserCredentialsRequestor;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserCredentialsRequestor credentialsRequestor;
    @Autowired
    CryptoUtils cryptoUtils;
    private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Message> responseFuture = new CompletableFuture<>();
        pendingResponses.put(correlationId, responseFuture);
        MessageProperties props = new MessageProperties();

        props.setHeader("correlationId", correlationId);
        props.setExpiration("5000");

			String encryptEmail = null;
			try {
				encryptEmail = this.cryptoUtils.encrypt(email);
			} catch (Exception e) {

				e.printStackTrace();
			}
	
        Message message = new Message(encryptEmail.getBytes(), props);


        credentialsRequestor.requestUserCredentials(message);

        try {
  
            Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);

            return convertMessageToUserDetails(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to receive correct response", e);
        } finally {
            pendingResponses.remove(correlationId);
        }
    }

    private UserDetailsDTO convertMessageToUserDetails(Message message) {
        try {
       
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(message.getBody(), UserDetailsDTO.class);
        } catch (Exception e) {
     
            throw new RuntimeException("Failed to convert message to UserDetails", e);
        }
    }

    public void receiveResponse(Message message) {

        String responseCorrelationId = (String) message.getMessageProperties().getHeader("correlationId");

        CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
        if (responseFuture != null) {
           responseFuture.complete(message);
        } else {
      }
    }
}
