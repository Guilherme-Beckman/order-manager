package com.ms.products.rabbitMQ.listener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.products.config.RabbitMQConfig;
import com.ms.products.model.product.ProductModel;
import com.ms.products.service.ProductService;
@Component
public class GetProductsByMenuIdListener {
	@Autowired
	private ProductService productService;
	
	@RabbitListener(queues = RabbitMQConfig.PRODUCTS_BY_MENU_ID_QUEUE)
	public List<ProductModel> receiveMenuIdId(@Payload String menuId) {
		return this.productService.findByMenuId(menuId);
	}
}
