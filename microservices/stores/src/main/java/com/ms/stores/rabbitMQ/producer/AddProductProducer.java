package com.ms.stores.rabbitMQ.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;
import com.ms.stores.model.products.ProductDTO;
import com.ms.stores.model.products.ProductModel;
import com.ms.stores.utils.message.MessageUtils;

@Component
public class AddProductProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MessageUtils messageUtils;

	public ProductModel addProduct(ProductDTO newProductDTO) {
		var message = this.messageUtils.createMessage(newProductDTO);
		byte[] linkedHashMapProduct = (byte[]) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.ADD_PRODUCT_EXCHANGE,
				RabbitMQConfig.ADD_PRODUCT_KEY, message);
		Message content = new Message(linkedHashMapProduct);
		ProductModel productModel = (ProductModel) this.messageUtils.convertMessage(content, ProductModel.class);
		return productModel;
	}

}
