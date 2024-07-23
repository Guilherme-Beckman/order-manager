package com.ms.stores.rabbitMQ.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;

@Component
public class AddProductMenuProducer {
	@Autowired
	@Qualifier("rabbitTemplateForProducts")
	RabbitTemplate rabbitTemplate;

	public void addProductMenu(MenuProductDTO menuProductDTO) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.ADD_PRODUCT_MENU_EXCHANGE, RabbitMQConfig.ADD_PRODUCT_MENU_KEY,
				menuProductDTO);

	}

}
