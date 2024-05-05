package com.ms.user.rabbitMQ.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;

@Component
public class UserReturnData{
	@Autowired
	public RabbitTemplate rabbitTemplate;
	
	public void returnUserData(String encryptedPassword) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.USER_FANOUT_EXCHANGE, RabbitMQConfig.USER_QUEUE, encryptedPassword);
	}
}
