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
import com.ms.auth.rabbitMQ.producer.UserCredentialsRequestor;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserCredentialsRequestor credentialsRequestor;
    
    private final ConcurrentHashMap<String, CompletableFuture<Message>> pendingResponses = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: Recebendo solicitação para o email: " + email);

        // cria id para identificação da mensagem
        String correlationId = UUID.randomUUID().toString();
        System.out.println("Criado correlationId: " + correlationId);

        // cria uma mensagem que pode ser completada futuramente
        CompletableFuture<Message> responseFuture = new CompletableFuture<>();
        System.out.println("Criado CompletableFuture para a resposta.");

        // relaciona o id com a mensagem futura
        pendingResponses.put(correlationId, responseFuture);
        System.out.println("Adicionado correlationId ao mapa de pendências.");

        // define um objeto para armazenar as propriedades
        MessageProperties props = new MessageProperties();
        System.out.println("Criadas propriedades da mensagem.");

        // adicionar o cabeçalho às propriedades, contendo o id
        props.setHeader("correlationId", correlationId);
        System.out.println("Adicionado cabeçalho 'correlationId' às propriedades.");

        // define as propriedades da mensagem
        Message message = new Message(email.getBytes(), props);
        System.out.println("Criada mensagem para o RabbitMQ com o email e correlationId.");

        // faz a requisição, pedindo as credenciais por meio do RabbitMQ
        credentialsRequestor.requestUserCredentials(message);
        System.out.println("Enviada requisição de credenciais ao RabbitMQ.");

        try {
            // espera a mensagem chegar até 5 segundos
            Message response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
            System.out.println("Resposta recebida do RabbitMQ.");

            // converte a mensagem para um DTO que estende a interface UserDetails
            return convertMessageToUserDetails(response);
        } catch (Exception e) {
            System.out.println("Erro ao receber ou converter a resposta: " + e.getMessage());
            throw new RuntimeException("Failed to receive correct response", e);
        } finally {
            System.out.println("Removendo correlationId do mapa de pendências.");
            pendingResponses.remove(correlationId);
        }
    }

    private UserDetailsDTO convertMessageToUserDetails(Message message) {
        try {
            System.out.println("Convertendo mensagem para UserDetailsDTO.");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(message.getBody(), UserDetailsDTO.class);
        } catch (Exception e) {
            System.out.println("Erro ao converter mensagem: " + e.getMessage());
            throw new RuntimeException("Failed to convert message to UserDetails", e);
        }
    }

    public void receiveResponse(Message message) {
        System.out.println("Recebendo resposta do RabbitMQ.");
        
        String responseCorrelationId = (String) message.getMessageProperties().getHeader("correlationId");
        System.out.println("CorrelationId recebido: " + responseCorrelationId);
        
        CompletableFuture<Message> responseFuture = pendingResponses.get(responseCorrelationId);
        if (responseFuture != null) {
            System.out.println("Completing the CompletableFuture with the received message.");
            responseFuture.complete(message);
        } else {
            System.out.println("Nenhum CompletableFuture encontrado para correlationId: " + responseCorrelationId);
        }
    }
}
