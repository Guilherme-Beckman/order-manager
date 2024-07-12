package com.ms.stores.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;

@Component
public class StoreServiceRegisterReturnDataProducer {
	@Autowired
	public RabbitTemplate rabbitTemplate;

	public void returnStoreData(Message store) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_STORE_REGISTER_STORE_DIRECT_EXCHANGE,
				RabbitMQConfig.RETURN_REGISTERED_STORE_RESPONSE_KEY, store);
	}
}
