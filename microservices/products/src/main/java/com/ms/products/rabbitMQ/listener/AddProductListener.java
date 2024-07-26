package com.ms.products.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.products.config.RabbitMQConfig;
import com.ms.products.model.product.ProductDTO;
import com.ms.products.model.product.ProductModel;
import com.ms.products.service.ProductService;
import com.ms.products.utils.MessageUtils;

@Component
public class AddProductListener {
	@Autowired
	private ProductService productService;
	@Autowired
	private MessageUtils messageUtils;

	@RabbitListener(queues = RabbitMQConfig.ADD_PRODUCT_QUEUE)
	public ProductModel receiveProducToAdd(@Payload Message message) {
		ProductDTO productModel = (ProductDTO) this.messageUtils.convertMessage(message, ProductDTO.class);

		return this.productService.addNewProduct(productModel);
	}
}
