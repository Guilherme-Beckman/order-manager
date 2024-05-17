package com.ms.user.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;

@Component
public class UserReturnData{
	@Autowired
	public RabbitTemplate rabbitTemplate;
	
	public void returnUserData(Message user) {
	
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_DIRECT_EXCHANGE, RabbitMQConfig.BINDINGKEY_RESPONSE, user);
	}
}
