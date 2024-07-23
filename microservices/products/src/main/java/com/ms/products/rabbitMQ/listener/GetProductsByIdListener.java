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
	@Autowired
	private MessageUtils messageUtils;

	@RabbitListener(queues = RabbitMQConfig.PRODUCTS_BY_ID_QUEUE)
	public Message receiveId(@Payload Message message) {
		var id = (String) this.messageUtils.convertMessage(message, String.class);
		var productModelDTO = this.productService.getProductModelDTOById(id);

		var message1 = this.messageUtils.createMessage(productModelDTO);

		return message1;
	}
}
