package com.ms.user.rabbitMQ.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
        byte[] jsonBytes = objectMapper.writeValueAsBytes(user);

        // Define as propriedades da mensagem
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("correlationId", correlationId);
        // Cria uma mensagem com os bytes do JSON
        return new Message(jsonBytes, messageProperties);
    }
}
