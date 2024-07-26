package com.ms.stores.rabbitMQ.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;
import com.ms.stores.utils.message.MessageUtils;

@Component
public class AddProductMenuProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MessageUtils messageUtils;

	public void addProductMenu(MenuProductDTO menuProductDTO) {
		var message = this.messageUtils.createMessage(menuProductDTO);
		rabbitTemplate.convertAndSend(RabbitMQConfig.ADD_PRODUCT_MENU_EXCHANGE, RabbitMQConfig.ADD_PRODUCT_MENU_KEY,
				message);

	}

}
