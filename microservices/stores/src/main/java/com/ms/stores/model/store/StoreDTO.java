package com.ms.stores.model.store;

import java.util.List;

import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.opening_hours.OpeningHoursDTO;

public record StoreDTO(
		String name,
		String email,
		String password,
		AddressDTO address,
		String phone,
		String CNPJ,
		List<OpeningHoursDTO> opening_hours

) {

}
