package com.ms.api_gateway.rabbitMQ.producer;

import org.springframework.amqp.core.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.api_gateway.config.RabbitMQConfig;

@Component
public class UserCredentialsProducerApiGateway {
    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void requestUserCredentials(Message email) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.AUTH_USER_USER_DETAILS_DIRECT_API_GATEWAY_EXCHANGE,
            RabbitMQConfig.LOAD_USER_DETAILS_REQUEST_API_GATEWAY_KEY,
            email
        );


    }
}
