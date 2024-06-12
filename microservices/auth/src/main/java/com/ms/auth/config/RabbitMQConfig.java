package com.ms.auth.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
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

public static final String USER_SERVICE_REQUEST_QUEUE = "user_service_request_queue";
public static final String USER_SERVICE_RESPONSE_QUEUE = "user_service_response_queue";
public static final String USER_SERVICE_DIRECT_EXCHANGE = "user_service_exchange";
public static final String USER_SERVICE_BINDINGKEY_REQUEST = "user_service.request";
public static final String USER_SERVICE_BINDINGKEY_RESPONSE = "user_service.response";

public static final String EMAIL_CODE_FANOUT_EXCHANGE = "email_code_fanout_exchange";
public static final String EMAIL_CODE_GENERATED_QUEUE = "email_code_genereted_queue";

public static final String USER_EMAIL_VALIDATE_FANOUT_EXCHANGE = "user_email_validate_fanout_exchange";
public static final String USER_EMAIL_VALIDATE_QUEUE = "user_email_validate_queue";

@Bean
public Queue authQueue(){
	return new Queue(AUTH_QUEUE, true);
}
@Bean
public Queue userQueue(){
	return new Queue(USER_QUEUE, true);
}
@Bean 
public DirectExchange exchange() {
	return new DirectExchange(AUTH_DIRECT_EXCHANGE);
	}
@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory); 
}
@Bean
public Binding bindRequest(DirectExchange exchange, @Qualifier("authQueue") Queue requestQueue) {
 return BindingBuilder.bind(requestQueue).to(exchange).with(BINDINGKEY_REQUEST);
}
@Bean
public Binding bindResponse(DirectExchange exchange, @Qualifier("userQueue") Queue responseQueue) {
 return BindingBuilder.bind(responseQueue).to(exchange).with(BINDINGKEY_RESPONSE);
}

@Bean
public Queue userServiceRequest() {
	return new Queue(USER_SERVICE_REQUEST_QUEUE, true);
}
@Bean
public Queue userServiceResponse() {
	return new Queue(USER_SERVICE_RESPONSE_QUEUE, true);
}
@Bean
public DirectExchange userServiceExchange() {
	return new DirectExchange(USER_SERVICE_DIRECT_EXCHANGE );
}
@Bean
public Binding bindRequestUserService(@Qualifier("userServiceExchange")DirectExchange exchange, @Qualifier("userServiceRequest") Queue resquestQueue) {
 return BindingBuilder.bind(resquestQueue).to(exchange).with(USER_SERVICE_BINDINGKEY_REQUEST);
}
@Bean
public Binding bindResponseUserService(@Qualifier("userServiceExchange")DirectExchange exchange, @Qualifier("userServiceResponse") Queue responseQueue) {
 return BindingBuilder.bind(responseQueue).to(exchange).with(USER_SERVICE_BINDINGKEY_RESPONSE);
}
@Bean
public Queue emailCodeQueueGenerated() {
	return new Queue(EMAIL_CODE_GENERATED_QUEUE, true);
}
@Bean 
public FanoutExchange emailCodeExchangeGenerated() {
	return new FanoutExchange(EMAIL_CODE_FANOUT_EXCHANGE);
}
@Bean
public Binding bindEmailCodeExchangeGenerated(@Qualifier("emailCodeExchangeGenerated")FanoutExchange exchange, @Qualifier("emailCodeQueueGenerated") Queue emailCodeQueueGenerated) {
 return BindingBuilder.bind(emailCodeQueueGenerated).to(exchange);
}

@Bean
public Queue userValidateEmailQueue() {
	return new Queue(USER_EMAIL_VALIDATE_QUEUE, true);
}
@Bean 
public FanoutExchange userValidadeExchange() {
	return new FanoutExchange(USER_EMAIL_VALIDATE_FANOUT_EXCHANGE);
}
@Bean
public Binding binduValidadeExchange(@Qualifier("userValidadeExchange")FanoutExchange exchange, @Qualifier("userValidateEmailQueue") Queue userValidateEmailQueue) {
 return BindingBuilder.bind(userValidateEmailQueue).to(exchange);
}
}
