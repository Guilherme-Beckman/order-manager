package com.ms.auth.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class EmailCodeProducer {
@Autowired
private RabbitTemplate rabbitTemplate;

public void produceEmailCode(Message message) {
	rabbitTemplate.convertAndSend(
	RabbitMQConfig.EMAIL_CODE_FANOUT_EXCHANGE,
	message);
}


}
