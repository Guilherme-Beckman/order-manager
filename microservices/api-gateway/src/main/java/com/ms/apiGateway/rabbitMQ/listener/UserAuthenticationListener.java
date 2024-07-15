package com.ms.apiGateway.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.apiGateway.config.RabbitMQConfig;
import com.ms.apiGateway.service.ReactiveCustomUserDetailsService;

@Component
public class UserAuthenticationListener{
	@Autowired
	private ReactiveCustomUserDetailsService detailsService;

	@RabbitListener(queues = RabbitMQConfig.RETURN_USER_DETAILS_API_GATEWAY_QUEUE)
	public void listenAuthQueue(@Payload Message encrytedUserDataOrNull) throws Exception {
		if (encrytedUserDataOrNull != null) {
			detailsService.receiveResponse(encrytedUserDataOrNull);
		} else {
			throw new Exception();
		}
	}
}
