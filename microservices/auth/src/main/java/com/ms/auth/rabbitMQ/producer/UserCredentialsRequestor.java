package com.ms.auth.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ms.auth.config.RabbitMQConfig;

@Component
public class UserCredentialsRequestor {
    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void requestUserCredentials(Message email) {
        System.out.println("requestUserCredentials: Enviando mensagem ao RabbitMQ."+" email: "+email.getBody().toString());

        // Convertendo e enviando a mensagem para o RabbitMQ
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.AUTH_DIRECT_EXCHANGE,
            RabbitMQConfig.BINDINGKEY_REQUEST,
            email
        );

        System.out.println("Mensagem enviada para o Exchange: " 
            + RabbitMQConfig.AUTH_DIRECT_EXCHANGE
            + " com a Binding Key: "
            + RabbitMQConfig.BINDINGKEY_REQUEST
        );
    }
}
