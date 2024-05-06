package com.ms.auth.rabbitMQ.consumer;


import org.springframework.amqp.core.Message;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Channel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.service.CustomUserDetailsService;

@Component
public class AuthenticationConsumer {
	@Autowired
	public CustomUserDetailsService detailsService;
	
@RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
public void listenAuthQueue(@Payload Message encrytedUserDataOrNull) throws Exception {
	if(!(encrytedUserDataOrNull == null)) {
		detailsService.receiveResponse(encrytedUserDataOrNull);
	}else {
		throw new Exception();
	}
}
}
