package com.ms.orders.rabbitMQ.producer;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.orders.config.RabbitMQConfig;
import com.ms.orders.model.product.ProductModelDTO;
import com.ms.orders.utils.message.MessageUtils;

@Component
public class GetProductByIdProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MessageUtils messageUtils;

	public ProductModelDTO requestProductsByIdProducer(String id) {
		var message = this.messageUtils.createMessage(id);

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> linkedHashMapProduct = (LinkedHashMap<String, Object>) rabbitTemplate
				.convertSendAndReceive(RabbitMQConfig.PRODUCT_BY_ID_EXCHANGE, RabbitMQConfig.PRODUCTS_BY_ID_KEY,
						message);

		var productModelDTO = this.mapToProductModelDTO(linkedHashMapProduct);

		return productModelDTO;
	}

	private ProductModelDTO mapToProductModelDTO(LinkedHashMap<String, Object> map) {
		var rating = map.get("rating");
		rating = rating.toString();
		rating = Float.parseFloat((String) rating);
		return new ProductModelDTO((String) map.get("id"), (String) map.get("ownerId"), (String) map.get("menuId"),
				(String) map.get("name"), (Integer) map.get("price"), (String) map.get("description"),
				(Boolean) map.get("availability"), (Float) rating, (Integer) map.get("reviewsCount"),
				(List<String>) map.get("reviews"));
	}
}
