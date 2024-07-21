package com.ms.email.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.config.RabbitMQConfig;
import com.ms.email.service.EmailService;

@Component
public class EmailCodeListener {
	@Autowired
	private EmailService emailService;

	@RabbitListener(queues = RabbitMQConfig.EMAIL_CODE_GENERATED_QUEUE)
	public void listenEmailCodeQueue(@Payload Message message) {
		System.out.println(message);
		if (message != null) {
			this.emailService.sendEmailCode(message);
		}
	}
}
