package com.ms.user.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;
import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.address.UserIdAddressIdDTO;
import com.ms.user.rabbitMQ.utils.MessageUtils;
import com.ms.user.service.UserService;

@Component
public class GetAddresByUserIdAdressIdProducer {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageUtils messageUtils;

	@RabbitListener(queues = RabbitMQConfig.ADDRESS_BY_USER_ID_ADRESS_ID_QUEUE)
	public Message receiveMapWithUserIdAdressId(@Payload Message message) {
		UserIdAddressIdDTO userIdAddressIdDTO = (UserIdAddressIdDTO) this.messageUtils.convertMessage(message,
				UserIdAddressIdDTO.class);
		AddressDTO addressDTO = this.userService.getAddressDTOByUserIdAddressId(userIdAddressIdDTO.userId(),
				userIdAddressIdDTO.addressId());

		var message1 = this.messageUtils.createMessage(addressDTO);

		return message1;
	}
}
