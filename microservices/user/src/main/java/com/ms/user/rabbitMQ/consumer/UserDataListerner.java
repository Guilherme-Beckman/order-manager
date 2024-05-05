package com.ms.user.rabbitMQ.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;

@Component
public class UserDataListerner {
	@RabbitListener(queues= RabbitMQConfig.USER_QUEUE)
	public void processUserData(@Payload String username) {
		System.out.println(username);
	}
}
