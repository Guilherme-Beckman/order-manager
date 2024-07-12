package com.ms.auth.dto.store;


import java.util.List;

import com.ms.auth.dto.address.AddressDTO;
import com.ms.auth.dto.store.opening_hours.OpeningHoursDTO;


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
