package com.ms.orders.rabbitMQ.producer;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.orders.config.RabbitMQConfig;
import com.ms.orders.exceptions.rest.AddressNotFoundInUserListException;
import com.ms.orders.model.address.AddressDTO;
import com.ms.orders.model.address.UserIdAddressIdDTO;
import com.ms.orders.model.product.ProductModelDTO;
import com.ms.orders.utils.message.MessageUtils;

@Component
public class GetAddresByUserIdAdressIdProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MessageUtils messageUtils;

	public AddressDTO requestAddressByUserIdAddressIdProducer(String userId, String addressId) {
		UserIdAddressIdDTO userAdressId = new UserIdAddressIdDTO(userId, addressId);
		var message = this.messageUtils.createMessage(userAdressId);

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) rabbitTemplate.convertSendAndReceive(
				RabbitMQConfig.ADDRESS_BY_USER_ID_ADRESS_ID_EXCHANGE, RabbitMQConfig.ADDRESS_BY_USER_ID_ADRESS_ID_KEY,
				message);
		if (linkedHashMap == null)
			throw new AddressNotFoundInUserListException();
		var newAddressDTO = this.mapToAddressDTO(linkedHashMap);
		return newAddressDTO;
	}

    private AddressDTO mapToAddressDTO(LinkedHashMap<String, Object> map) {
        String userId = (String) map.get("userId");
        String street = (String) map.get("street");
        String number = map.get("number") != null ? (String) map.get("number") : "";
        String complement = (String) map.get("complement");
        String neighborhood = (String) map.get("neighborhood");
        String city = (String) map.get("city");
        String state = (String) map.get("state");
        String zipCode = (String) map.get("zipCode");

        return new AddressDTO(userId, street, number, complement, neighborhood, city, state, zipCode);
    }

}
