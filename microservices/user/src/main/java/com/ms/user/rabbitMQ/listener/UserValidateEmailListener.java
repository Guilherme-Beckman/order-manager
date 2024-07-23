package com.ms.user.rabbitMQ.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;
import com.ms.user.service.UserService;

@Component
public class UserValidateEmailListener {
	@Autowired
	private UserService userService;

	@RabbitListener(queues = RabbitMQConfig.USER_EMAIL_VALIDATE_QUEUE)
	public void validateUserEmail(@Payload String email) {
		this.userService.validateUserEmail(email);
	}
}
