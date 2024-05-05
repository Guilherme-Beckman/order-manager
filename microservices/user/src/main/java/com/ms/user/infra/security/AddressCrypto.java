package com.ms.user.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.user.model.address.AddressDTO;

@Component
public class AddressCrypto {
	@Autowired
	CryptoUtils cryptoUtils;
	


	public AddressDTO cryptoAddressData(AddressDTO addressDTO) throws Exception {

	     String street = this.cryptoUtils.encrypt(addressDTO.getStreet()); 
	     String number = this.cryptoUtils.encrypt(addressDTO.getNumber());
	     String complement = this.cryptoUtils.encrypt(addressDTO.getComplement());
	     String neighborhood = this.cryptoUtils.encrypt(addressDTO.getNeighborhood());
	     String city = this.cryptoUtils.encrypt(addressDTO.getCity());
	     String state = this.cryptoUtils.encrypt(addressDTO.getState());
	     String zipCode = this.cryptoUtils.encrypt(addressDTO.getZipCode());

	     
	     addressDTO.setStreet(street);
	     addressDTO.setNumber(number);
	     addressDTO.setComplement(complement);
	     addressDTO.setNeighborhood(neighborhood);
	     addressDTO.setCity(city);
	     addressDTO.setState(state);
	     addressDTO.setState(zipCode);
	     
	     return addressDTO;
	     
	}
}
