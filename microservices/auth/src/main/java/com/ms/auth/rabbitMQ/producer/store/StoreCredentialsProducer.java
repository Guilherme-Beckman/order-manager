package com.ms.auth.rabbitMQ.producer.store;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class StoreCredentialsProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void requestStoreCredentials(Message email) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_STORE_STORE_DETAILS_DIRECT_EXCHANGE,
				RabbitMQConfig.LOAD_STORE_DETAILS_REQUEST_KEY, email);

	}

}
