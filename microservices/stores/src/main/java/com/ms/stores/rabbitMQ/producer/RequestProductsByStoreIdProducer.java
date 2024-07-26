package com.ms.stores.rabbitMQ.producer;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;
import com.ms.stores.model.products.ProductModel;
import com.ms.stores.utils.message.MessageUtils;

@Component
public class RequestProductsByStoreIdProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MessageUtils messageUtils;

	public List<ProductModel> requestProductsByStoreIdProducer(String storeId) {
		var message = this.messageUtils.createMessage(storeId);
		byte[] products = (byte[]) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.PRODUCT_BY_STORE_EXCHANGE,
				RabbitMQConfig.PRODUCTS_BY_STORE_KEY, message);
		Message newMessage = new Message(products);
		@SuppressWarnings("unchecked")
		List<ProductModel> models = (List<ProductModel>) this.messageUtils.convertMessage(newMessage, List.class);
		return models;
	}
}
