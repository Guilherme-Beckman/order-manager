package com.ms.user.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	public static final String USER_QUEUE = "auth-service.user-data";
	public static final String USER_FANOUT_EXCHANGE = "user-service.fanout";
	@Bean
	public Queue userQueue() {
		return new Queue (USER_QUEUE,true );
	}
	@Bean
	public FanoutExchange userFanoutExchange() {
		return new FanoutExchange(USER_FANOUT_EXCHANGE);
	}
	
	@Bean 
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}


}
