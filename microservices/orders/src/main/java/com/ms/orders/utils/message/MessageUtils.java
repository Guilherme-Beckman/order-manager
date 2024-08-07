package com.ms.orders.utils.message;

import org.springframework.amqp.core.Message;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.orders.exceptions.rest.message.ConvertMessageException;

@Component
public class MessageUtils {
	@Autowired
	private ObjectMapper objectMapper;

	public Message createMessage(Object content, String correlationId) {
		try {
			byte[] body = this.objectMapper.writeValueAsBytes(content);
			MessageProperties props = new MessageProperties();
			props.setCorrelationId(correlationId);
			props.setExpiration("5000");
			return new Message(body, props);
		} catch (Exception e) {
			throw new ConvertMessageException("Error while converting message into UserDetails");
		}
	}

	public Message createMessage(Object content) {
		try {
			byte[] body = this.objectMapper.writeValueAsBytes(content);
			MessageProperties props = new MessageProperties();
			props.setExpiration("5000");
			return new Message(body, props);
		} catch (Exception e) {
			throw new ConvertMessageException("Error while converting message into UserDetails");
		}
	}

	public Object convertMessage(Message message, Class<?> objectClass) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var body = objectMapper.readValue(message.getBody(), objectClass);

			return body;
		} catch (Exception e) {
			throw new ConvertMessageException("Error while converting message into UserDetails");
		}

	}

}
