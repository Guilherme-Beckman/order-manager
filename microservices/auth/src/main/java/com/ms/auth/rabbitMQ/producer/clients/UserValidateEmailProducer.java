package com.ms.auth.rabbitMQ.producer.clients;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserValidateEmailProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void produceValidateUserEmail(String email) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EMAIL_VALIDATE_FANOUT_EXCHANGE, "", email);
	}
}
