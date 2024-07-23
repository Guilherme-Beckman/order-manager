package com.ms.products.rabbitMQ.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.products.config.RabbitMQConfig;
import com.ms.products.model.product.ProductDTO;
import com.ms.products.model.product.ProductModel;
import com.ms.products.service.ProductService;

@Component
public class AddProductListener {
	@Autowired
	private ProductService productService;

	@RabbitListener(queues = RabbitMQConfig.ADD_PRODUCT_QUEUE)
	public ProductModel receiveProducToAdd(@Payload ProductDTO productDTO) {
		return this.productService.addNewProduct(productDTO);
	}
}
