package com.ms.auth.rabbitMQ.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.exceptions.auth.message.ConvertMessageException;

@Component
public class EmailResetLinkUserEmailProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	public void produceResetLinkUserEmail(String email, String newPassword) {
		Map<String, String> payload = new HashMap<>();
		payload.put("email", email);
		payload.put("newPassword", newPassword);
		String messageBody = null;
		try {
			messageBody = objectMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			throw new ConvertMessageException();
		}

		rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EMAIL_RESET_LINK_FANOUT_EXCHANGE, "", messageBody);
	}

}
