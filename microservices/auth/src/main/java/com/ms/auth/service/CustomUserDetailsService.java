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
import com.ms.auth.dto.UserDTO;
import com.ms.auth.dto.UserDetailsDTO;
import com.ms.auth.infra.security.CryptoUtils;
import com.ms.auth.rabbitMQ.producer.UserCredentialsRequestor;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    public UserCredentialsRequestor credentialsRequestor;
    @Autowired
    public CryptoUtils cryptoUtils;
    private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService: Attempting to load user by email: " + email);

        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Message> responseFuture = new CompletableFuture<>();
        pendingResponses.put(correlationId, responseFuture);
        MessageProperties props = new MessageProperties();

        props.setHeader("correlationId", correlationId);
        props.setExpiration("5000");

        String encryptEmail = null;
        try {
            System.out.println("CustomUserDetailsService: Encrypting email: " + email);
            encryptEmail = this.cryptoUtils.encrypt(email);
            System.out.println("CustomUserDetailsService: Email encrypted successfully.");
        } catch (Exception e) {
            System.out.println("CustomUserDetailsService: Failed to encrypt email: " + e.getMessage());
           
        }

        Message message = new Message(encryptEmail.getBytes(), props);
        System.out.println("CustomUserDetailsService: Sending message to request user credentials with correlationId: " + correlationId);
        credentialsRequestor.requestUserCredentials(message);

        try {
            System.out.println("CustomUserDetailsService: Awaiting response for correlationId: " + correlationId);
            Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
            System.out.println("CustomUserDetailsService: Response received for correlationId: " + correlationId);
            return convertMessageToUserDetails(response);
        } catch (Exception e) {
            System.out.println("CustomUserDetailsService: Exception occurred while awaiting response: " + e.getMessage());
           
            return null;
        } finally {
            pendingResponses.remove(correlationId);
            System.out.println("CustomUserDetailsService: CorrelationId removed from pending responses: " + correlationId);
        }
    }

    private UserDetailsDTO convertMessageToUserDetails(Message message) {
        try {
            System.out.println("CustomUserDetailsService: Converting message to UserDetailsDTO.");
            ObjectMapper objectMapper = new ObjectMapper();
            var user = objectMapper.readValue(message.getBody(), UserDetailsDTO.class);
            System.out.println("CustomUserDetailsService: Message converted to UserDetailsDTO: " + user);
            return user;
        } catch (Exception e) {
            System.out.println("CustomUserDetailsService: Failed to convert message to UserDetails: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("CustomUserDetailsService: Failed to convert message to UserDetails", e);
        }
    }

    public void receiveResponse(Message message) {
        System.out.println("CustomUserDetailsService: Received response message.");
        String responseCorrelationId = (String) message.getMessageProperties().getHeader("correlationId");
        System.out.println("CustomUserDetailsService: Response correlationId: " + responseCorrelationId);

        CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
        if (responseFuture != null) {
            responseFuture.complete(message);
            System.out.println("CustomUserDetailsService: Response future completed for correlationId: " + responseCorrelationId);
        } else {
            System.out.println("CustomUserDetailsService: No pending response found for correlationId: " + responseCorrelationId);
        }
    }
}
