package com.ms.auth.rabbitMQ.listener.store;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.service.store.StoreAuthenticationService;

@Component
public class StoreServiceRegisterListener {

	@Autowired
	public StoreAuthenticationService storeAuthenticationService;

	@RabbitListener(queues = RabbitMQConfig.RETURN_REGISTERED_STORE_QUEUE)
	public void listenAuthQueue(@Payload Message store) {
		try {
			if (store != null) {

				storeAuthenticationService.receiveResponse(store);
			} else {
				throw new Exception("Received null message.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
