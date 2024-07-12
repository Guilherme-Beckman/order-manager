package com.ms.stores.rabbitMQ.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.stores.model.StoreModel;

@Component
public class StoreMessageConverter {
	private final ObjectMapper objectMapper;

	public StoreMessageConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public Message convertStoreToMessage(StoreModel store, String correlationId) {
		if (store == null) {
			throw new IllegalArgumentException("User cannot be null");
		}

		byte[] menssageBytes = null;
		try {
			menssageBytes = objectMapper.writeValueAsBytes(store);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setCorrelationId(correlationId);
		messageProperties.setExpiration("5000");
		return new Message(menssageBytes, messageProperties);
	}

}
