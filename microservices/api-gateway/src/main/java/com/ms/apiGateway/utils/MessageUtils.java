package com.ms.apiGateway.utils;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.apiGateway.dto.StoreDetailsDTO;
import com.ms.apiGateway.dto.UserDetailsDTO;
import com.ms.apiGateway.exceptions.auth.message.ConvertMessageToUserDetailsException;

@Component
public class MessageUtils {
	@Autowired
	private ObjectMapper objectMapper;

	public UserDetailsDTO convertMessageToUserDetails(Message message) {
		try {
			byte[] body = message.getBody();
			UserDetailsDTO user = objectMapper.readValue(body, UserDetailsDTO.class);

			return user;
		} catch (Exception e) {
			throw new ConvertMessageToUserDetailsException("Error while converting message into UserDetails");
		}
	}
	   public StoreDetailsDTO convertMessageToStoreDetails(Message message) {
           try {
               ObjectMapper objectMapper = new ObjectMapper();
               var user = objectMapper.readValue(message.getBody(), StoreDetailsDTO.class);
               return user;
           } catch (Exception e) {
               throw new ConvertMessageToUserDetailsException("Error while converting message into UserDetails");      
   }
	   }

	public Message createMessage(Object content, String correlationId) {
		try {
			byte[] body = this.objectMapper.writeValueAsBytes(content);
			MessageProperties props = new MessageProperties();
			props.setCorrelationId(correlationId);
			props.setExpiration("1000");

			Message message = new Message(body, props);

			return message;
		} catch (Exception e) {
			throw new ConvertMessageToUserDetailsException("Error while converting message into UserDetails");
		}
	}

	public String generateCorrelationId() {
		String correlationId = UUID.randomUUID().toString();
		return correlationId;
	}
}
