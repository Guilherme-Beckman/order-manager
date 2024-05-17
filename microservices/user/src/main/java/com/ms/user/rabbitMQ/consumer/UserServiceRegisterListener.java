package com.ms.user.rabbitMQ.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.config.RabbitMQConfig;
import com.ms.user.model.user.UserDTO;
import com.ms.user.rabbitMQ.producer.UserServiceRegisterReturnData;
import com.ms.user.rabbitMQ.utils.UserMessageConverter;
import com.ms.user.service.UserService;

@Component
public class UserServiceRegisterListener {
    
    @Autowired
    public UserService userService;
    
    @Autowired
    public ObjectMapper objectMapper;
    
    @Autowired
    UserMessageConverter userMessageConverter;
    
    @Autowired 
    UserServiceRegisterReturnData registerReturnData;

    @RabbitListener(queues = RabbitMQConfig.USER_SERVICE_REQUEST_QUEUE)
    public void registerUser(@Payload Message userDTO) {
        System.out.println("UserServiceRegisterListener: Received registration request message from queue: " + RabbitMQConfig.USER_SERVICE_REQUEST_QUEUE);

        try {
            System.out.println("UserServiceRegisterListener: Deserializing userDTO message.");
            var user = this.objectMapper.readValue(userDTO.getBody(), UserDTO.class);
            System.out.println("UserServiceRegisterListener: UserDTO deserialized: " + user);
            
            System.out.println("UserServiceRegisterListener: Inserting user into database.");
            var userdb = this.userService.insertUser(user);
            System.out.println("UserServiceRegisterListener: User inserted into database: " + userdb);
            
            System.out.println("UserServiceRegisterListener: Converting userdb to message.");
            var userBytes = this.userMessageConverter.convertUserToMessage(userdb, userDTO.getMessageProperties().getHeader("correlationId"));
            
            System.out.println("UserServiceRegisterListener: User converted to message bytes: "+userBytes);
            
            System.out.println("UserServiceRegisterListener: Sending user data back to requester.");
            this.registerReturnData.returnUserData(userBytes);
            System.out.println("UserServiceRegisterListener: User data sent back successfully.");
        } catch (Exception e) {
            System.out.println("UserServiceRegisterListener: Exception occurred while processing registration request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
