package com.ms.auth.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserCredentialsProducer {
    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void requestUserCredentials(Message email) {
    	

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.AUTH_DIRECT_EXCHANGE,
            RabbitMQConfig.BINDINGKEY_REQUEST,
            email
        );


    }
}
