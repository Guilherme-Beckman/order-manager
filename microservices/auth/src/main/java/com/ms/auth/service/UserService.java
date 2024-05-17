package com.ms.auth.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.auth.dto.UserDTO;
import com.ms.auth.dto.UserDetailsDTO;
import com.ms.auth.rabbitMQ.producer.UserCredentialsRequestor;
import com.ms.auth.rabbitMQ.producer.UserServiceRegisterRequestor;

@Service
public class UserService {
    private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public UserServiceRegisterRequestor registerRequestor;

    public UserDetails registerUser(UserDTO userDTO) throws JsonProcessingException {
        System.out.println("UserService: Starting user registration process for email: " + userDTO.email());

        if (this.customUserDetailsService.loadUserByUsername(userDTO.email()) != null) {
            System.out.println("UserService: User with email " + userDTO.email() + " already exists.");
            return null;
        }

        System.out.println("UserService: Encrypting password for user: " + userDTO.email());
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());

        UserDTO newUser = new UserDTO(userDTO.cpf(), encryptedPassword, userDTO.name(), userDTO.lastName(), userDTO.email(), userDTO.address());
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Message> responseFuture = new CompletableFuture<>();
        pendingResponses.put(correlationId, responseFuture);

        System.out.println("UserService: Generated correlationId: " + correlationId);

        MessageProperties props = new MessageProperties();
        props.setHeader("correlationId", correlationId);
        props.setExpiration("5000");

        byte[] body = objectMapper.writeValueAsBytes(newUser);
        Message message = new Message(body, props);
        System.out.println("UserService: Sending register request for user: " + message);

        System.out.println("UserService: Sending register request for user: " + userDTO.email());

        registerRequestor.requestRegister(message);
        try {
            System.out.println("UserService: Awaiting response for correlationId: " + correlationId);
            Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
            System.out.println("UserService: Received response for correlationId: " + correlationId);
            return convertMessageToUserDetails(response);
        } catch (Exception e) {
            System.out.println("UserService: Exception occurred while waiting for response: " + e.getMessage());
           
            return null;
        } finally {
            pendingResponses.remove(correlationId);
            System.out.println("UserService: Removed correlationId from pending responses: " + correlationId);
        }
    }

    private UserDetailsDTO convertMessageToUserDetails(Message message) {
        try {
            System.out.println("UserService: Converting message to UserDetailsDTO");
            ObjectMapper objectMapper = new ObjectMapper();
            var user = objectMapper.readValue(message.getBody(), UserDetailsDTO.class);
            System.out.println("UserService: Message converted to UserDetailsDTO: " + user);
            return user;
        } catch (Exception e) {
            System.out.println("UserService: Failed to convert message to UserDetails: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("UserService: Failed to convert message to UserDetails", e);
        }
    }

    public void receiveResponse(Message message) {
        System.out.println("UserService: Received response with correlationId: " + (String) message.getMessageProperties().getHeader("correlationId"));
        String responseCorrelationId = (String) message.getMessageProperties().getHeader("correlationId");

        CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
        if (responseFuture != null) {
            responseFuture.complete(message);
            System.out.println("UserService: Completed future for correlationId: " + responseCorrelationId);
        } else {
            System.out.println("UserService: No pending future found for correlationId: " + responseCorrelationId);
        }
    }
}
