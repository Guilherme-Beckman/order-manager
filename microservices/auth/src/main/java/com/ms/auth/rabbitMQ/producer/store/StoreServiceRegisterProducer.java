package com.ms.auth.rabbitMQ.producer.store;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.auth.config.RabbitMQConfig;

@Component
public class StoreServiceRegisterProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void requestRegister(Message storeDTO) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.AUTH_STORE_REGISTER_STORE_DIRECT_EXCHANGE,
                    RabbitMQConfig.REGISTER_STORE_REQUEST_KEY,
                    storeDTO);   
    }
}
