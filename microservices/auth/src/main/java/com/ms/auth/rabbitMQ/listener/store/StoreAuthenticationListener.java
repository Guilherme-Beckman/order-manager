package com.ms.auth.rabbitMQ.listener.store;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.service.store.CustomStoreDetailsService;

@Component
public class StoreAuthenticationListener {
	@Autowired
	private CustomStoreDetailsService detailsService;

	@RabbitListener(queues = RabbitMQConfig.RETURN_STORE_DETAILS_QUEUE)
	public void listenAuthQueue(@Payload Message encrytedStoreDataOrNull) throws Exception {
		if (encrytedStoreDataOrNull != null) {
			detailsService.receiveResponse(encrytedStoreDataOrNull);
		} else {
			throw new Exception();
		}
	}
}
