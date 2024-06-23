package com.ms.auth.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.service.UserAuthenticationService;

@Component
public class UserServiceRegisterListener {

	@Autowired
	public UserAuthenticationService userService;

	@RabbitListener(queues = RabbitMQConfig.RETURN_REGISTERED_USER_QUEUE)
	public void listenAuthQueue(@Payload Message user) {
		try {
			if (user != null) {

				userService.receiveResponse(user);
			} else {
				throw new Exception("Received null message.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
