package com.ms.stores.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.address.AddressModel;
import com.ms.stores.service.AddressService;

@Component
public class DecryptAddresses {
	@Autowired
	private AddressService addressService;
	@Autowired
	private CryptoUtils cryptoUtils;

	public AddressDTO decryptAddresses(String id) {
		AddressModel addressModel = this.addressService.getAddressById(id);
		String userId = addressModel.getUserId();
		String street = addressModel.getStreet();
		String number = addressModel.getNumber();
		String complement = addressModel.getComplement();
		String neighborhood = addressModel.getNeighborhood();
		String city = addressModel.getCity();
		String state = addressModel.getState();
		String zipCode = addressModel.getZipCode();
		if (!street.equals("null"))
			street = this.cryptoUtils.decrypt(street);
		if (!number.equals("null"))
			number = this.cryptoUtils.decrypt(number);
		if (!complement.equals("null"))
			complement = this.cryptoUtils.decrypt(complement);
		if (!neighborhood.equals("null"))
			neighborhood = this.cryptoUtils.decrypt(neighborhood);
		if (!city.equals("null"))
			city = this.cryptoUtils.decrypt(city);
		if (!state.equals("null"))
			state = this.cryptoUtils.decrypt(state);
		if (!zipCode.equals("null"))
			zipCode = this.cryptoUtils.decrypt(zipCode);

		AddressDTO addressDTO = new AddressDTO(userId, street, number, complement, neighborhood, city, state, zipCode);

		return addressDTO;
	}
}
