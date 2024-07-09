package com.ms.stores.model;

import java.util.List;

import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.opening_hours.OpeningHoursDTO;

public record StoreDTO(
		String name,
		String email,
		String password,
		List<AddressDTO> addresses,
		String phone,
		String CNPJ,
		List<OpeningHoursDTO> opening_hours

) {

}