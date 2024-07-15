package com.ms.stores.rabbitMQ.listener;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.stores.config.RabbitMQConfig;
import com.ms.stores.infra.security.CryptoUtils;
import com.ms.stores.rabbitMQ.producer.StoreReturnDataProducer;
import com.ms.stores.rabbitMQ.utils.StoreMessageConverter;
import com.ms.stores.service.StoreService;

@Component
public class StoreDataListener {

	@Autowired
	public StoreService storeService;

	@Autowired
	public StoreReturnDataProducer returnData;

	@Autowired
	public CryptoUtils cryptoUtils;

	@Autowired
	public StoreMessageConverter messageConverter;

	@RabbitListener(queues = RabbitMQConfig.LOAD_STORE_DETAILS_QUEUE)
	public void processStoreData(@Payload Message email) {
		System.out.println("esta no listener da store 4 ");
		if (email != null) {
			String emailP = new String(email.getBody());
			emailP = emailP.replace("\"", "");
			var encryptedEmail = this.cryptoUtils.encrypt(emailP);
			var findedStore = this.storeService.getStoreByEmail(encryptedEmail);
			System.out.println(findedStore + "achou isso dai ");
			System.out.println(findedStore != null);
			if (findedStore != null) {
				System.out.println("ta dentro de extamaente onde voce pensa que está");
				try {
					var storeBytes = this.messageConverter.convertStoreToMessage(findedStore,
							email.getMessageProperties().getCorrelationId());
					System.out.println("esta retornado o user: "+storeBytes);
					this.returnData.returnStoreData(storeBytes);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
			}
			System.out.println("não achou aqui no listener tlgf");
		}
	}
}
