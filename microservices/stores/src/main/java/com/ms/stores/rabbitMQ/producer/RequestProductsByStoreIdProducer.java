package com.ms.stores.rabbitMQ.producer;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ms.products.model.product.ProductModel;
import com.ms.stores.config.RabbitMQConfig;
@Component
public class RequestProductsByStoreIdProducer {
	@Autowired
	@Qualifier("rabbitTemplateForProducts")
	private RabbitTemplate rabbitTemplate;
	
	public List<ProductModel> requestProductsByStoreIdProducer(String storeId) {

		List<ProductModel> products = (List<ProductModel>) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.PRODUCT_BY_STORE_EXCHANGE,
				RabbitMQConfig.PRODUCTS_BY_STORE_KEY, storeId);

		return products;
	}
}
