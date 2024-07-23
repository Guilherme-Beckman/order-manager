package com.ms.orders.model.address;

public record AddressDTO(String userId, String street, String number, String complement, String neighborhood,
		String city, String state, String zipCode) {
}
