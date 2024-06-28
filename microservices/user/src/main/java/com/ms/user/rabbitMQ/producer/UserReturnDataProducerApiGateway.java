package com.ms.user.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;

@Component
public class UserReturnDataProducerApiGateway {
	@Autowired
	public RabbitTemplate rabbitTemplate;

	public void returnUserData(Message user) {
		System.out.println(user);
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_USER_USER_DETAILS_DIRECT_API_GATEWAY_EXCHANGE,
				RabbitMQConfig.RETURN_USER_DETAILS_RESPONSE_API_GATEWAY_KEY, user);
	}
}
