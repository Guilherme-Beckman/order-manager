package com.ms.auth.rabbitMQ.producer.clients;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserCredentialsProducer {
	@Autowired
	public RabbitTemplate rabbitTemplate;

	public void requestUserCredentials(Message email) {

		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_USER_USER_DETAILS_DIRECT_EXCHANGE,
				RabbitMQConfig.LOAD_USER_DETAILS_REQUEST_KEY, email);

	}
}
