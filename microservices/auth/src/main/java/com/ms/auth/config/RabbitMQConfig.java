package com.ms.auth.config;

import org.springframework.amqp.core.FanoutExchange;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

public static final String AUTH_QUEUE = "user-service.encrytedUserDataOrNull" ;
public static final String AUTH_FANOUT_EXCHANGE = "auth-service.fanout";

@Bean
public Queue authQueue(){
	return new Queue(AUTH_QUEUE, true);
}

@Bean
public FanoutExchange authFanoutExchange(){
	return new FanoutExchange(AUTH_FANOUT_EXCHANGE);
}

@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory); 
}



}
