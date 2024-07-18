package com.ms.stores.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.stores.config.RabbitMQConfig;
import com.ms.stores.model.store.StoreDTO;
import com.ms.stores.rabbitMQ.producer.StoreServiceRegisterReturnDataProducer;
import com.ms.stores.rabbitMQ.utils.StoreMessageConverter;
import com.ms.stores.service.StoreService;

@Component
public class StoreServiceRegisterListener {

	@Autowired
	public StoreService userService;

	@Autowired
	public ObjectMapper objectMapper;

	@Autowired
	private StoreMessageConverter storeMessageConverter;

	@Autowired
	private StoreServiceRegisterReturnDataProducer registerReturnData;

	@RabbitListener(queues = RabbitMQConfig.REGISTER_STORE_QUEUE)
	public void registerStore(@Payload Message storeDTO) {

		try {
			var store = this.objectMapper.readValue(storeDTO.getBody(), StoreDTO.class);
			var storedb = this.userService.insertUser(store);
			var storeBytes = this.storeMessageConverter.convertStoreToMessage(storedb,
					storeDTO.getMessageProperties().getCorrelationId());
			this.registerReturnData.returnStoreData(storeBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
