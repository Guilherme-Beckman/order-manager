package com.ms.products.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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

	public static final String EMAIL_RESET_LINK_FANOUT_EXCHANGE = "email_reset_link_fanout_exchange";
	public static final String EMAIL_RESET_LINK_GENERATED_QUEUE = "email_reset_link_genereted_queue";
	public static final String USER_EMAIL_RESET_LINK_FANOUT_EXCHANGE = "user_email_reset_link_fanout_exchange";
	public static final String USER_EMAIL_RESET_LINK_QUEUE = "user_email_reset_link_queue";

	// Store
	public static final String LOAD_STORE_DETAILS_QUEUE = "store_ms.load_store_details_queue";
	public static final String RETURN_STORE_DETAILS_QUEUE = "auth_ms.return_store_details_queue";
	public static final String AUTH_STORE_STORE_DETAILS_DIRECT_EXCHANGE = "auth_ms.store_ms_store_details_direct_exchange";
	public static final String LOAD_STORE_DETAILS_REQUEST_KEY = "load.store.details.request";
	public static final String RETURN_STORE_DETAILS_RESPONSE_KEY = "return.store.details.response";

	public static final String REGISTER_STORE_QUEUE = "store_ms.register_store_queue";
	public static final String RETURN_REGISTERED_STORE_QUEUE = "auth_ms.return_registered_store_queue";
	public static final String AUTH_STORE_REGISTER_STORE_DIRECT_EXCHANGE = "auth_ms.register_store_direct_exchange";
	public static final String REGISTER_STORE_REQUEST_KEY = "register.store.request";
	public static final String RETURN_REGISTERED_STORE_RESPONSE_KEY = "return.registered.store.response";

	public static final String LOAD_STORE_DETAILS_API_GATEWAY_QUEUE = "store_ms.load_store_details_api_gateway_queue";
	public static final String RETURN_STORE_DETAILS_API_GATEWAY_QUEUE = "auth_ms.return_store_details_api_gateway_queue";
	public static final String AUTH_STORE_STORE_DETAILS_DIRECT_API_GATEWAY_EXCHANGE = "auth_ms.store_ms_store_details_direct_api_gateway_exchange";
	public static final String LOAD_STORE_DETAILS_REQUEST_API_GATEWAY_KEY = "load.store.details.api_gateway_request";
	public static final String RETURN_STORE_DETAILS_RESPONSE_API_GATEWAY_KEY = "return.store.details.api_gateway_response";

	// products

	public static final String ADD_PRODUCT_QUEUE = "add_product.queue";
	public static final String ADD_PRODUCT_EXCHANGE = "add_product.exchange";
	public static final String ADD_PRODUCT_KEY = "add_product_key";

	public static final String PRODUCTS_BY_STORE_ID_QUEUE = "products_by_store_id.queue";
	public static final String PRODUCT_BY_STORE_EXCHANGE = "products_by_store_id.exchange";
	public static final String PRODUCTS_BY_STORE_KEY = "products_by_store_id.key";

	public static final String ADD_PRODUCT_MENU_QUEUE = "add_product_menu.queue";
	public static final String ADD_PRODUCT_MENU_EXCHANGE = "add_product_menu.exchange";
	public static final String ADD_PRODUCT_MENU_KEY = "add_product_menu.key";

	public static final String PRODUCTS_BY_MENU_ID_QUEUE = "products_by_menu_id.queue";
	public static final String PRODUCT_BY_MENU_EXCHANGE = "products_by_menu_id.exchange";
	public static final String PRODUCTS_BY_MENU_KEY = "products_by_menu_id.key";

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
			@Qualifier("loadUserDetailsQueue") Queue loadUserDetailsApiGatewayQueue) {
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

	@Bean
	public Queue emailResetLinkQueueGenerated() {
		return new Queue(EMAIL_RESET_LINK_GENERATED_QUEUE, true);
	}

	@Bean
	public FanoutExchange emailResetLinkExchangeGenerated() {
		return new FanoutExchange(EMAIL_RESET_LINK_FANOUT_EXCHANGE);
	}

	@Bean
	public Binding bindEmailResetLinkExchangeGenerated(
			@Qualifier("emailResetLinkExchangeGenerated") FanoutExchange exchange,
			@Qualifier("emailResetLinkQueueGenerated") Queue emailResetLinkQueueGenerated) {
		return BindingBuilder.bind(emailResetLinkQueueGenerated).to(exchange);
	}

	@Bean
	public Queue userResetLinkEmailQueue() {
		return new Queue(USER_EMAIL_RESET_LINK_QUEUE, true);
	}

	@Bean
	public FanoutExchange userResetLinkExchange() {
		return new FanoutExchange(USER_EMAIL_RESET_LINK_FANOUT_EXCHANGE);
	}

	@Bean
	public Binding bindResetLinkExchange(@Qualifier("userResetLinkExchange") FanoutExchange exchange,
			@Qualifier("userResetLinkEmailQueue") Queue userResetLinkEmailQueue) {
		return BindingBuilder.bind(userResetLinkEmailQueue).to(exchange);
	}

	// Store
	@Bean
	public Queue loadStoreDetailsQueue() {
		return new Queue(LOAD_STORE_DETAILS_QUEUE, true);
	}

	@Bean
	public Queue returnStoreDetailsQueue() {
		return new Queue(RETURN_STORE_DETAILS_QUEUE, true);
	}

	@Bean
	public DirectExchange authStoreDirectExchange() {
		return new DirectExchange(AUTH_STORE_STORE_DETAILS_DIRECT_EXCHANGE);
	}

	@Bean
	public Binding loadStoreDetailsRequestKey(
			@Qualifier("authStoreDirectExchange") DirectExchange authStoreDirectExchange,
			@Qualifier("loadStoreDetailsQueue") Queue loadStoreDetailsQueue) {
		return BindingBuilder.bind(loadStoreDetailsQueue).to(authStoreDirectExchange)
				.with(LOAD_STORE_DETAILS_REQUEST_KEY);
	}

	@Bean
	public Binding returnStoreDetailsRequestKey(
			@Qualifier("authStoreDirectExchange") DirectExchange authStoreDirectExchange,
			@Qualifier("returnStoreDetailsQueue") Queue returnStoreDetailsQueue) {
		return BindingBuilder.bind(returnStoreDetailsQueue).to(authStoreDirectExchange)
				.with(RETURN_STORE_DETAILS_RESPONSE_KEY);
	}

	@Bean
	public Queue registerStoreQueue() {
		return new Queue(REGISTER_STORE_QUEUE, true);
	}

	@Bean
	public Queue returnRegisteredStoreQueue() {
		return new Queue(RETURN_REGISTERED_STORE_QUEUE, true);
	}

	@Bean
	public DirectExchange authStoreRegisterStoreDirectExchange() {
		return new DirectExchange(AUTH_STORE_REGISTER_STORE_DIRECT_EXCHANGE);
	}

	@Bean
	public Binding registerStoreRequestKey(
			@Qualifier("authStoreRegisterStoreDirectExchange") DirectExchange authStoreRegisterStoreDirectExchange,
			@Qualifier("registerStoreQueue") Queue registerStoreQueue) {
		return BindingBuilder.bind(registerStoreQueue).to(authStoreRegisterStoreDirectExchange)
				.with(REGISTER_STORE_REQUEST_KEY);
	}

	@Bean
	public Binding returnRegisteredStoreResponseKey(
			@Qualifier("authStoreRegisterStoreDirectExchange") DirectExchange authStoreRegisterStoreDirectExchange,
			@Qualifier("returnRegisteredStoreQueue") Queue returnRegisteredStoreQueue) {
		return BindingBuilder.bind(returnRegisteredStoreQueue).to(authStoreRegisterStoreDirectExchange)
				.with(RETURN_REGISTERED_STORE_RESPONSE_KEY);
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

//		products
	@Bean
	public Queue addProductQueue() {
		return new Queue(ADD_PRODUCT_QUEUE);
	}

	@Bean
	public TopicExchange addProductExchange() {
		return new TopicExchange(ADD_PRODUCT_EXCHANGE);
	}

	@Bean
	public Binding binding(@Qualifier("addProductQueue") Queue addProductQueue,
			@Qualifier("addProductExchange") TopicExchange addProductExchange) {
		return BindingBuilder.bind(addProductQueue).to(addProductExchange).with(ADD_PRODUCT_KEY);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue productByStoreIdQueue() {
		return new Queue(PRODUCTS_BY_STORE_ID_QUEUE);
	}

	@Bean
	public TopicExchange productByStoreIdExchange() {
		return new TopicExchange(PRODUCT_BY_STORE_EXCHANGE);
	}

	@Bean
	public Binding productByStoreIdExchangeBindingKey(@Qualifier("productByStoreIdQueue") Queue productByStoreIdQueue,
			@Qualifier("productByStoreIdExchange") TopicExchange productByStoreIdExchange) {
		return BindingBuilder.bind(productByStoreIdQueue).to(productByStoreIdExchange).with(PRODUCTS_BY_STORE_KEY);
	}

	@Bean
	public Queue addProductMenuQueue() {
		return new Queue(ADD_PRODUCT_MENU_QUEUE);
	}

	@Bean
	public TopicExchange addProductMenuExchange() {
		return new TopicExchange(ADD_PRODUCT_MENU_EXCHANGE);
	}

	@Bean
	public Binding bindingMenu(@Qualifier("addProductMenuQueue") Queue addProductMenuQueue,
			@Qualifier("addProductMenuExchange") TopicExchange addProductMenuExchange) {
		return BindingBuilder.bind(addProductMenuQueue).to(addProductMenuExchange).with(ADD_PRODUCT_MENU_KEY);
	}

	@Bean
	public Queue productByMenuIdQueue() {
		return new Queue(PRODUCTS_BY_MENU_ID_QUEUE);
	}

	@Bean
	public TopicExchange productByMenuIdExchange() {
		return new TopicExchange(PRODUCT_BY_MENU_EXCHANGE);
	}

	@Bean
	public Binding productByMenuIdExchangeBindingKey(@Qualifier("productByMenuIdQueue") Queue productByMenuIdQueue,
			@Qualifier("productByMenuIdExchange") TopicExchange productByMenuIdExchange) {
		return BindingBuilder.bind(productByMenuIdQueue).to(productByMenuIdExchange).with(PRODUCTS_BY_MENU_KEY);
	}

}
