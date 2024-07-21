package com.ms.user.model.user;

import java.util.List;

import com.ms.user.model.address.AddressDTO;

public record UserPerfilDTO(
		String id,
		String name,
		String lastName,
		String email,
		String phone,
		List<AddressDTO> adresses
		
		) {

}
