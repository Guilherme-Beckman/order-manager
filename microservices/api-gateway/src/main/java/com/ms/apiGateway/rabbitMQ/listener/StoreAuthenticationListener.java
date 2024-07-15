package com.ms.apiGateway.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.apiGateway.config.RabbitMQConfig;
import com.ms.apiGateway.service.ReactiveCustomStoreDetailsService;

@Component
public class StoreAuthenticationListener{
	@Autowired
	private ReactiveCustomStoreDetailsService detailsService;

	@RabbitListener(queues = RabbitMQConfig.RETURN_STORE_DETAILS_API_GATEWAY_QUEUE)
	public void listenAuthQueue(@Payload Message encrytedStoreDataOrNull) throws Exception {
		if (encrytedStoreDataOrNull != null) {
			detailsService.receiveResponse(encrytedStoreDataOrNull);
		} else {
			throw new Exception();
		}
	}
}
