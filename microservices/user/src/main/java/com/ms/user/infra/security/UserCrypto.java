package com.ms.user.infra.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.user.UserDTO;
import com.ms.user.model.user.UserModel;
import com.ms.user.model.user.UserPerfilDTO;


@Component
public class UserCrypto {
	@Autowired
	private CryptoUtils cryptoUtils;
	@Autowired
	private DecryptAddresses addressCrypto;
	public UserDTO cryptoUserData(UserDTO userDTO)  {

		String cpf = this.cryptoUtils.encrypt(userDTO.cpf()); 
		String name = this.cryptoUtils.encrypt(userDTO.name());
		String lastName = this.cryptoUtils.encrypt(userDTO.lastName());
		String email = this.cryptoUtils.encrypt(userDTO.email());
		var encryptedUser = new UserDTO(cpf, userDTO.password(), name, lastName, email, userDTO.address());
	     
	     return encryptedUser;
	     
	}
	public UserPerfilDTO decryptUserData(UserModel userModel) {
		String id = userModel.getId();
		String name = this.cryptoUtils.decrypt(userModel.getName());
		String lastname = this.cryptoUtils.decrypt(userModel.getLastName());
		String email = this.cryptoUtils.decrypt(userModel.getEmail());
		List<AddressDTO> addressDTOs = this.addressCrypto.decryptAddresses(userModel.getAddress());
		UserPerfilDTO userPerfilDTO = new UserPerfilDTO(id, name, lastname, email, addressDTOs);
		return userPerfilDTO;
		
	}
}
