package com.ms.auth.rabbitMQ.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserCredentialsRequestor {
	@Autowired
	public RabbitTemplate rabbitTemplate;
	
	public void requestUserCredentials(String username) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_FANOUT_EXCHANGE, RabbitMQConfig.AUTH_QUEUE, username);
	}
}
