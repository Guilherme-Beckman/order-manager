package com.ms.auth.rabbitMQ.listener.clients;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.service.clients.CustomUserDetailsService;

@Component
public class UserAuthenticationListener {
	@Autowired
	private CustomUserDetailsService detailsService;

	@RabbitListener(queues = RabbitMQConfig.RETURN_USER_DETAILS_QUEUE)
	public void listenAuthQueue(@Payload Message encrytedUserDataOrNull) throws Exception {
		if (encrytedUserDataOrNull != null) {
			detailsService.receiveResponse(encrytedUserDataOrNull);
		} else {
			throw new Exception();
		}
	}
}
