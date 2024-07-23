package com.ms.products.rabbitMQ.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.products.config.RabbitMQConfig;
import com.ms.products.service.ProductService;

@Component
public class AddProductMenuListener {
	@Autowired
	private ProductService productService;

	@RabbitListener(queues = RabbitMQConfig.ADD_PRODUCT_MENU_QUEUE)
	public void receiveProducToAddMenu(@Payload MenuProductDTO menuProductDTO) {
		this.productService.addProductMenu(menuProductDTO);
	}
}
