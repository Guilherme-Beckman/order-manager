package com.ms.auth.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserServiceRegisterProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void requestRegister(Message userDTO) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.AUTH_USER_REGISTER_USER_DIRECT_EXCHANGE,
                    RabbitMQConfig.REGISTER_USER_REQUEST_KEY,
                    userDTO);   
    }
}
