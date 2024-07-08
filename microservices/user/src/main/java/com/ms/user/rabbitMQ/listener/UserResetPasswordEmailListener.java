package com.ms.user.rabbitMQ.listener;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.config.RabbitMQConfig;
import com.ms.user.exceptions.auth.message.ConvertMessageException;
import com.ms.user.service.UserService;

@Component
public class UserResetPasswordEmailListener {

	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = RabbitMQConfig.USER_EMAIL_RESET_LINK_QUEUE)
	public void validateUserEmail(@Payload String message) {
		Map<String, String> payload;
		try {
			payload = objectMapper.readValue(message, new TypeReference<Map<String, String>>() {
			});
		} catch (JsonProcessingException e) {
			throw new ConvertMessageException();
		}
		String email = payload.get("email");
		String newPassword = payload.get("newPassword");

		userService.changePassword(email, newPassword);
	}
}
