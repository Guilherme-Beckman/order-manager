package com.ms.orders.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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

	public static final String PRODUCTS_BY_ID_QUEUE = "products_by_id.queue";
	public static final String PRODUCT_BY_ID_EXCHANGE = "products_by_id.exchange";
	public static final String PRODUCTS_BY_ID_KEY = "products_by_id.key";

	public static final String ADDRESS_BY_USER_ID_ADRESS_ID_QUEUE = "address_by_user_id_address_id.queue";
	public static final String ADDRESS_BY_USER_ID_ADRESS_ID_EXCHANGE = "address_by_user_id_address_id.exchange";
	public static final String ADDRESS_BY_USER_ID_ADRESS_ID_KEY = "address_by_user_id_address_id.key";

	public RabbitTemplate rabbitTemplateForProducts(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		rabbitTemplate.setReplyTimeout(6000);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue productByIdQueue() {
		return new Queue(PRODUCTS_BY_ID_QUEUE);
	}

	@Bean
	public TopicExchange productByIdExchange() {
		return new TopicExchange(PRODUCT_BY_ID_EXCHANGE);
	}

	@Bean
	public Binding productByIdExchangeBindingKey(@Qualifier("productByIdQueue") Queue productByIdQueue,
			@Qualifier("productByIdExchange") TopicExchange productByIdExchange) {
		return BindingBuilder.bind(productByIdQueue).to(productByIdExchange).with(PRODUCTS_BY_ID_KEY);
	}

	@Bean
	public Queue addressByUserIdAddressIdQueue() {
		return new Queue(ADDRESS_BY_USER_ID_ADRESS_ID_QUEUE);
	}

	@Bean
	public TopicExchange addressUserIdAddressIdExchange() {
		return new TopicExchange(ADDRESS_BY_USER_ID_ADRESS_ID_EXCHANGE);
	}

	@Bean
	public Binding addressUserIdAddressIdExchangeBindingKey(
			@Qualifier("addressByUserIdAddressIdQueue") Queue addressByUserIdAddressIdQueue,
			@Qualifier("addressUserIdAddressIdExchange") TopicExchange addressUserIdAddressIdExchange) {
		return BindingBuilder.bind(addressByUserIdAddressIdQueue).to(addressUserIdAddressIdExchange)
				.with(ADDRESS_BY_USER_ID_ADRESS_ID_KEY);
	}
}
