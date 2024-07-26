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
public class RequestProductsByMenuIdProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private MessageUtils messageUtils;

	public List<ProductModel> requestProductsByMenuIdProducer(String menuId) {
		var message = this.messageUtils.createMessage(menuId);
		Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.PRODUCT_BY_MENU_EXCHANGE,
				RabbitMQConfig.PRODUCTS_BY_MENU_KEY, message);
		byte[] products = (byte[]) response;
		Message newMessage = new Message(products);
		@SuppressWarnings("unchecked")
		List<ProductModel> productsModels = (List<ProductModel>) this.messageUtils.convertMessage(newMessage,
				List.class);
		return productsModels;
	}
}
