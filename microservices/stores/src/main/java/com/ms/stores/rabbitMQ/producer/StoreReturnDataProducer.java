package com.ms.stores.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;

@Component
public class StoreReturnDataProducer{
	@Autowired
	public RabbitTemplate rabbitTemplate;
	
	public void returnStoreData(Message store) {
		System.out.println("ta aquiaaaaaaaa a");
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_STORE_STORE_DETAILS_DIRECT_EXCHANGE, RabbitMQConfig.RETURN_STORE_DETAILS_RESPONSE_KEY, store);
	}
}
