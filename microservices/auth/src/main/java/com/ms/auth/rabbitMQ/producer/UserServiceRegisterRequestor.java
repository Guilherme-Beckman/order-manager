package com.ms.auth.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserServiceRegisterRequestor {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void requestRegister(Message userDTO) {
        System.out.println("UserServiceRegisterRequestor: Preparing to send registration request to RabbitMQ.");

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.USER_SERVICE_DIRECT_EXCHANGE,
                    RabbitMQConfig.USER_SERVICE_BINDINGKEY_REQUEST,
                    userDTO);

            System.out.println("UserServiceRegisterRequestor: Successfully sent registration request to RabbitMQ.");
            System.out.println("UserServiceRegisterRequestor: Exchange: " + RabbitMQConfig.USER_SERVICE_DIRECT_EXCHANGE);
            System.out.println("UserServiceRegisterRequestor: Routing Key: " + RabbitMQConfig.USER_SERVICE_BINDINGKEY_REQUEST);
            System.out.println("UserServiceRegisterRequestor: Message: " + new String(userDTO.getBody()));
        } catch (Exception e) {
            System.out.println("UserServiceRegisterRequestor: Failed to send registration request to RabbitMQ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
