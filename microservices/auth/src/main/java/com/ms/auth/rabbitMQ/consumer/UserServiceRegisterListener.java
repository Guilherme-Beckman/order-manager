package com.ms.auth.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;
import com.ms.auth.service.UserService;

@Component
public class UserServiceRegisterListener {
    
    @Autowired
    public UserService userService;
    
    @RabbitListener(queues = RabbitMQConfig.USER_SERVICE_RESPONSE_QUEUE)
    public void listenAuthQueue(@Payload Message user) {
        try {
            System.out.println("UserServiceRegisterListener: Received message from queue: " + RabbitMQConfig.USER_QUEUE);
            if (user != null) {
                userService.receiveResponse(user);
                System.out.println("UserServiceRegisterListener: Message processed successfully.");
            } else {
                System.out.println("UserServiceRegisterListener: Received null message.");
                throw new Exception("Received null message.");
            }
        } catch (Exception e) {
            System.out.println("UserServiceRegisterListener: Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
