package com.ms.auth.rabbitMQ.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class AuthenticationConsumer {
@RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE)
public void listenAuthQueue(@Payload String string) {
	System.out.println(string);
}
}
