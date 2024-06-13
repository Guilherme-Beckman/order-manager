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
                    RabbitMQConfig.USER_SERVICE_DIRECT_EXCHANGE,
                    RabbitMQConfig.USER_SERVICE_BINDINGKEY_REQUEST,
                    userDTO);   
    }
}
