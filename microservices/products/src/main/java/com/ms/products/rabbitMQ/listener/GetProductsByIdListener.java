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
public class GetProductsByIdListener {
	@Autowired
	private ProductService productService;
	@Autowired MessageUtils messageUtils;
	@RabbitListener(queues = RabbitMQConfig.PRODUCTS_BY_ID_QUEUE)
	public Message receiveId(@Payload Message message) {
		var id = (String) this.messageUtils.convertMessage(message, String.class);
		var productModelDTO = this.productService.getProductModelDTOById(id);
		System.out.println(productModelDTO);
		var message1 =  this.messageUtils.createMessage(productModelDTO);
		System.out.println(message1.getClass());
		System.out.println(message1);
		return message1;
	}
}
