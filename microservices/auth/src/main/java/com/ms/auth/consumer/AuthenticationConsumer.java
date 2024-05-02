package com.ms.auth.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConsumer {
@RabbitListener(queues = "${broker.queue.auth.name}")
public void listenAuthQueue(@Payload String string) {
	System.out.println(string);
}
}
