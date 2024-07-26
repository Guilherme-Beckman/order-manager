package com.ms.products.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.products.config.RabbitMQConfig;
import com.ms.products.service.ProductService;
import com.ms.products.utils.MessageUtils;

@Component
public class AddProductMenuListener {
	@Autowired
	private ProductService productService;
	@Autowired
	private MessageUtils messageUtils;

	@RabbitListener(queues = RabbitMQConfig.ADD_PRODUCT_MENU_QUEUE)
	public void receiveProducToAddMenu(@Payload Message message) {
		MenuProductDTO menuProductDTO = (MenuProductDTO) this.messageUtils.convertMessage(message,
				MenuProductDTO.class);
		this.productService.addProductMenu(menuProductDTO);
	}
}
