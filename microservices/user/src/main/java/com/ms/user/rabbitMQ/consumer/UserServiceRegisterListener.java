package com.ms.user.rabbitMQ.consumer;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
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
         
        try {
            var user = this.objectMapper.readValue(userDTO.getBody(), UserDTO.class);
            var userdb = this.userService.insertUser(user);
            var userBytes = this.userMessageConverter.convertUserToMessage(userdb, userDTO.getMessageProperties().getCorrelationId());
            this.registerReturnData.returnUserData(userBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
