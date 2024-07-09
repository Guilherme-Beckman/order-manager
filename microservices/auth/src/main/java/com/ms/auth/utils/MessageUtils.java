package com.ms.auth.utils;

import java.util.UUID;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.auth.dto.clients.UserDetailsDTO;
import com.ms.auth.exceptions.auth.message.ConvertMessageToUserDetailsException;
@Component
public class MessageUtils {
	@Autowired
	private ObjectMapper objectMapper;
    public UserDetailsDTO convertMessageToUserDetails(Message message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var user = objectMapper.readValue(message.getBody(), UserDetailsDTO.class);
            return user;
        } catch (Exception e) {
            throw new ConvertMessageToUserDetailsException("Error while converting message into UserDetails");      
}
       
} 
    public Message createMessage (Object content, String correlationId)  {
    	try {
        	byte[] body = this.objectMapper.writeValueAsBytes(content);
        	MessageProperties props = new MessageProperties();
        	props.setCorrelationId(correlationId);    
        	props.setExpiration("5000");
        	return new Message(body, props);
        }catch (Exception e) {
        	throw new ConvertMessageToUserDetailsException("Error while converting message into UserDetails");      
        }
}
    public String generateCorrelationId() {
    	return UUID.randomUUID().toString();
    }
}