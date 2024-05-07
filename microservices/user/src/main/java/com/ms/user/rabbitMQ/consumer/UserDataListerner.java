package com.ms.user.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;
import com.ms.user.infra.security.CryptoUtils;
import com.ms.user.rabbitMQ.producer.UserReturnData;
import com.ms.user.rabbitMQ.utils.UserMessageConverter;
import com.ms.user.service.UserService;
@Component
public class UserDataListerner {

    @Autowired
    public UserService userService;

    @Autowired
    public UserReturnData returnData;

    @Autowired
    public CryptoUtils cryptoUtils;

    @Autowired
    public UserMessageConverter messageConverter;
    
    @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE)
    public void processUserData(@Payload Message email) throws Exception {
 
   
        if (email != null) {
   

            var emailP = new String(email.getBody());


            var findedUser = this.userService.getUserByEmail(emailP);

    var userBytes = this.messageConverter.convertUserToMessage(findedUser, email.getMessageProperties().getHeader("correlationId"));
 

            this.returnData.returnUserData(userBytes);
        } else {
        this.returnData.returnUserData(null);
        }
    }
}
