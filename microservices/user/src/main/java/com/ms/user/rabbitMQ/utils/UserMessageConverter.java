package com.ms.user.rabbitMQ.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.model.user.UserModel;
@Component
public class UserMessageConverter {
	private final ObjectMapper objectMapper;


    UserMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    

    public Message convertUserToMessage(UserModel user, String correlationId)  {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        byte[] menssageBytes = null;
		try {
			menssageBytes = objectMapper.writeValueAsBytes(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationId);
        messageProperties.setExpiration("5000");
        return new Message(menssageBytes, messageProperties);
    }
  
}
