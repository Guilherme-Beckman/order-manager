package com.ms.apiGateway.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.apiGateway.config.RabbitMQConfig;

@Component
public class StoreCredentialsProducerApiGateway {
	@Autowired
	public RabbitTemplate rabbitTemplate;

	public void requestStoreCredentials(Message email) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_STORE_STORE_DETAILS_DIRECT_API_GATEWAY_EXCHANGE,
				RabbitMQConfig.LOAD_STORE_DETAILS_REQUEST_API_GATEWAY_KEY, email);

	}
}
