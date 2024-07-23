package com.ms.stores.rabbitMQ.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ms.products.model.product.ProductModel;
import com.ms.stores.config.RabbitMQConfig;
import com.ms.stores.model.products.ProductDTO;

@Component
public class AddProductProducer {
	@Autowired
	@Qualifier("rabbitTemplateForProducts")
	RabbitTemplate rabbitTemplate;

	public ProductModel addProduct(ProductDTO newProductDTO) {

		ProductModel addedProduct = (ProductModel) rabbitTemplate.convertSendAndReceive(
				RabbitMQConfig.ADD_PRODUCT_EXCHANGE, RabbitMQConfig.ADD_PRODUCT_KEY, newProductDTO);

		return addedProduct;
	}

}
