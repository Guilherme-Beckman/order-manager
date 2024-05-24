package com.ms.user.rabbitMQ.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.model.user.UserModel;
@Component
public class UserMessageConverter {
	private final ObjectMapper objectMapper;


    UserMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    

    public Message convertUserToMessage(UserModel user, String correlationId) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("O objeto User não pode ser nulo");
        }

        // Converte o objeto User para JSON
        byte[] menssageBytes = objectMapper.writeValueAsBytes(user);

        // Define as propriedades da mensagem
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationId);
        messageProperties.setExpiration("5000");
        // Cria uma mensagem com os bytes do JSON
        return new Message(menssageBytes, messageProperties);
    }
  
}
