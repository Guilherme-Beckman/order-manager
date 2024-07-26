package com.ms.products.rabbitMQ.listener;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.products.config.RabbitMQConfig;
import com.ms.products.model.product.ProductModel;
import com.ms.products.service.ProductService;
import com.ms.products.utils.MessageUtils;

@Component
public class GetProductsByStoreIdListener {
	@Autowired
	private ProductService productService;
	@Autowired
	private MessageUtils messageUtils;

	@RabbitListener(queues = RabbitMQConfig.PRODUCTS_BY_STORE_ID_QUEUE)
	public List<ProductModel> receiveStoreId(@Payload Message message) {
		String storeId = (String) this.messageUtils.convertMessage(message, String.class);
		return this.productService.findByStoreId(storeId);
	}
}
