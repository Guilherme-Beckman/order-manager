package com.ms.apiGateway.config;

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

	public static final String LOAD_USER_DETAILS_QUEUE = "user_ms.load_user_details_queue";
	public static final String RETURN_USER_DETAILS_QUEUE = "auth_ms.return_user_details_queue";
	public static final String AUTH_USER_USER_DETAILS_DIRECT_EXCHANGE = "auth_ms.user_ms_user_details_direct_exchange";
	public static final String LOAD_USER_DETAILS_REQUEST_KEY = "load.user.details.request";
	public static final String RETURN_USER_DETAILS_RESPONSE_KEY = "return.user.details.response";

	public static final String REGISTER_USER_QUEUE = "user_ms.register_user_queue";
	public static final String RETURN_REGISTERED_USER_QUEUE = "auth_ms.return_registered_user_queue";
	public static final String AUTH_USER_REGISTER_USER_DIRECT_EXCHANGE = "auth_ms.register_user_direct_exchange";
	public static final String REGISTER_USER_REQUEST_KEY = "register.user.request";
	public static final String RETURN_REGISTERED_USER_RESPONSE_KEY = "return.registered.user.response";

	public static final String EMAIL_CODE_FANOUT_EXCHANGE = "email_code_fanout_exchange";
	public static final String EMAIL_CODE_GENERATED_QUEUE = "email_code_genereted_queue";

	public static final String USER_EMAIL_VALIDATE_FANOUT_EXCHANGE = "user_email_validate_fanout_exchange";
	public static final String USER_EMAIL_VALIDATE_QUEUE = "user_email_validate_queue";

	public static final String LOAD_USER_DETAILS_API_GATEWAY_QUEUE = "user_ms.load_user_details_api_gateway_queue";
	public static final String RETURN_USER_DETAILS_API_GATEWAY_QUEUE = "auth_ms.return_user_details_api_gateway_queue";
	public static final String AUTH_USER_USER_DETAILS_DIRECT_API_GATEWAY_EXCHANGE = "auth_ms.user_ms_user_details_direct_api_gateway_exchange";
	public static final String LOAD_USER_DETAILS_REQUEST_API_GATEWAY_KEY = "load.user.details.api_gateway_request";
	public static final String RETURN_USER_DETAILS_RESPONSE_API_GATEWAY_KEY = "return.user.details.api_gateway_response";

	// store

	public static final String LOAD_STORE_DETAILS_API_GATEWAY_QUEUE = "store_ms.load_store_details_api_gateway_queue";
	public static final String RETURN_STORE_DETAILS_API_GATEWAY_QUEUE = "auth_ms.return_store_details_api_gateway_queue";
	public static final String AUTH_STORE_STORE_DETAILS_DIRECT_API_GATEWAY_EXCHANGE = "auth_ms.store_ms_store_details_direct_api_gateway_exchange";
	public static final String LOAD_STORE_DETAILS_REQUEST_API_GATEWAY_KEY = "load.store.details.api_gateway_request";
	public static final String RETURN_STORE_DETAILS_RESPONSE_API_GATEWAY_KEY = "return.store.details.api_gateway_response";

	@Bean
	public Queue loadUserDetailsQueue() {
		return new Queue(LOAD_USER_DETAILS_QUEUE, true);
	}

	@Bean
	public Queue returnUserDetailsQueue() {
		return new Queue(RETURN_USER_DETAILS_QUEUE, true);
	}

	@Bean
	public DirectExchange authUserDirectExchange() {
		return new DirectExchange(AUTH_USER_USER_DETAILS_DIRECT_EXCHANGE);
	}

	@Bean
	public Binding loadUserDetailsRequestKey(@Qualifier("authUserDirectExchange") DirectExchange authUserDirectExchange,
			@Qualifier("loadUserDetailsQueue") Queue loadUserDetailsQueue) {
		return BindingBuilder.bind(loadUserDetailsQueue).to(authUserDirectExchange).with(LOAD_USER_DETAILS_REQUEST_KEY);
	}

	@Bean
	public Binding returnUserDetailsRequestKey(
			@Qualifier("authUserDirectExchange") DirectExchange authUserDirectExchange,
			@Qualifier("returnUserDetailsQueue") Queue returnUserDetailsQueue) {
		return BindingBuilder.bind(returnUserDetailsQueue).to(authUserDirectExchange)
				.with(RETURN_USER_DETAILS_RESPONSE_KEY);
	}

	@Bean
	public Queue registerUserQueue() {
		return new Queue(REGISTER_USER_QUEUE, true);
	}

	@Bean
	public Queue returnRegisteredUserQueue() {
		return new Queue(RETURN_REGISTERED_USER_QUEUE, true);
	}

	@Bean
	public DirectExchange authUserRegisterUserDirectExchange() {
		return new DirectExchange(AUTH_USER_REGISTER_USER_DIRECT_EXCHANGE);
	}

	@Bean
	public Binding registerUserRequestKey(
			@Qualifier("authUserRegisterUserDirectExchange") DirectExchange authUserRegisterUserDirectExchange,
			@Qualifier("registerUserQueue") Queue registerUserQueue) {
		return BindingBuilder.bind(registerUserQueue).to(authUserRegisterUserDirectExchange)
				.with(REGISTER_USER_REQUEST_KEY);
	}

	@Bean
	public Binding returnRegisteredUserResponseKey(
			@Qualifier("authUserRegisterUserDirectExchange") DirectExchange authUserRegisterUserDirectExchange,
			@Qualifier("returnRegisteredUserQueue") Queue returnRegisteredUserQueue) {
		return BindingBuilder.bind(returnRegisteredUserQueue).to(authUserRegisterUserDirectExchange)
				.with(RETURN_REGISTERED_USER_RESPONSE_KEY);
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
	public Binding bindEmailCodeExchangeGenerated(@Qualifier("emailCodeExchangeGenerated") FanoutExchange exchange,
			@Qualifier("emailCodeQueueGenerated") Queue emailCodeQueueGenerated) {
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
	public Binding binduValidadeExchange(@Qualifier("userValidadeExchange") FanoutExchange exchange,
			@Qualifier("userValidateEmailQueue") Queue userValidateEmailQueue) {
		return BindingBuilder.bind(userValidateEmailQueue).to(exchange);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public Queue loadUserDetailsApiGatewayQueue() {
		return new Queue(LOAD_USER_DETAILS_API_GATEWAY_QUEUE, true);
	}

	@Bean
	public Queue returnUserDetailsApiGatewayQueue() {
		return new Queue(RETURN_USER_DETAILS_API_GATEWAY_QUEUE, true);
	}

	@Bean
	public DirectExchange authUserDirectApiGatewayExchange() {
		return new DirectExchange(AUTH_USER_USER_DETAILS_DIRECT_API_GATEWAY_EXCHANGE);
	}

	@Bean
	public Binding loadUserDetailsRequestApiGatewayKey(
			@Qualifier("authUserDirectApiGatewayExchange") DirectExchange authUserDirectApiGatewayExchange,
			@Qualifier("loadUserDetailsApiGatewayQueue") Queue loadUserDetailsApiGatewayQueue) {
		return BindingBuilder.bind(loadUserDetailsApiGatewayQueue).to(authUserDirectApiGatewayExchange)
				.with(LOAD_USER_DETAILS_REQUEST_API_GATEWAY_KEY);
	}

	@Bean
	public Binding returnUserDetailsRequestApiGatewayKey(
			@Qualifier("authUserDirectApiGatewayExchange") DirectExchange authUserDirectApiGatewayExchange,
			@Qualifier("returnUserDetailsApiGatewayQueue") Queue returnUserDetailsApiGatewayQueue) {
		return BindingBuilder.bind(returnUserDetailsApiGatewayQueue).to(authUserDirectApiGatewayExchange)
				.with(RETURN_USER_DETAILS_RESPONSE_API_GATEWAY_KEY);
	}

	// store
	@Bean
	public Queue loadStoreDetailsApiGatewayQueue() {
		return new Queue(LOAD_STORE_DETAILS_API_GATEWAY_QUEUE, true);
	}

	@Bean
	public Queue returnStoreDetailsApiGatewayQueue() {
		return new Queue(RETURN_STORE_DETAILS_API_GATEWAY_QUEUE, true);
	}

	@Bean
	public DirectExchange authStoreDirectApiGatewayExchange() {
		return new DirectExchange(AUTH_STORE_STORE_DETAILS_DIRECT_API_GATEWAY_EXCHANGE);
	}

	@Bean
	public Binding loadStoreDetailsRequestApiGatewayKey(
			@Qualifier("authStoreDirectApiGatewayExchange") DirectExchange authStoreDirectApiGatewayExchange,
			@Qualifier("loadStoreDetailsApiGatewayQueue") Queue loadStoreDetailsApiGatewayQueue) {
		return BindingBuilder.bind(loadStoreDetailsApiGatewayQueue).to(authStoreDirectApiGatewayExchange)
				.with(LOAD_STORE_DETAILS_REQUEST_API_GATEWAY_KEY);
	}

	@Bean
	public Binding returnStoreDetailsRequestApiGatewayKey(
			@Qualifier("authStoreDirectApiGatewayExchange") DirectExchange authStoreDirectApiGatewayExchange,
			@Qualifier("returnStoreDetailsApiGatewayQueue") Queue returnStoreDetailsApiGatewayQueue) {
		return BindingBuilder.bind(returnStoreDetailsApiGatewayQueue).to(authStoreDirectApiGatewayExchange)
				.with(RETURN_STORE_DETAILS_RESPONSE_API_GATEWAY_KEY);
	}

}
