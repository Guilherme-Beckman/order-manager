package com.ms.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

public static final String AUTH_QUEUE = "user_request_queue";
public static final String USER_QUEUE = "user_response_queue" ;
public static final String AUTH_DIRECT_EXCHANGE = "user_exchange";
public static final String BINDINGKEY_REQUEST = "user.request";
public static final String BINDINGKEY_RESPONSE = "user.response";

@Bean
public Queue authQueue(){
	return new Queue(AUTH_QUEUE, true);
}
@Bean
public Queue userQueue(){
	return new Queue(USER_QUEUE, true);
}
@Bean 
DirectExchange exchange() {
	return new DirectExchange(AUTH_DIRECT_EXCHANGE);
	}
@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory); 
}
@Bean
public Binding bindRequest(DirectExchange exchange, @Qualifier("authQueue") Queue resquestQueue) {
 return BindingBuilder.bind(resquestQueue).to(exchange).with(BINDINGKEY_REQUEST);
}
@Bean
public Binding bindResponse(DirectExchange exchange, @Qualifier("userQueue") Queue responseQueue) {
 return BindingBuilder.bind(responseQueue).to(exchange).with(BINDINGKEY_RESPONSE);
}
}
