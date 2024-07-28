package com.ms.stores.utils.message;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.stores.exceptions.rest.ConvertMessageException;
import com.ms.stores.model.products.ProductModel;

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
			e.printStackTrace();
			throw new ConvertMessageException("Error while converting message into UserDetails");
		}

	}

	@SuppressWarnings("unchecked")
	public ProductModel mapToProductModelDTO(LinkedHashMap<String, Object> map) {
		var rating = map.get("rating");
		rating = rating.toString();
		rating = Float.parseFloat((String) rating);
		return new ProductModel((String) map.get("id"), (String) map.get("ownerId"), (String) map.get("menuId"),
				(String) map.get("name"), (Integer) map.get("price"), (String) map.get("description"),
				(Boolean) map.get("availability"), (Float) rating, (Integer) map.get("reviewsCount"),
				(List<String>) map.get("reviews"));
	}
}
