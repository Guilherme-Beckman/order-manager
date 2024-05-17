package com.ms.user.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ms.user.model.user.UserDTO;


@Component
public class UserCrypto {
	@Autowired
	CryptoUtils cryptoUtils;


	public UserDTO cryptoUserData(UserDTO userDTO) throws Exception {

		String cpf = this.cryptoUtils.encrypt(userDTO.cpf()); 
		//String password = this.cryptoUtils.encrypt(userDTO.password());
		String name = this.cryptoUtils.encrypt(userDTO.name());
		String lastName = this.cryptoUtils.encrypt(userDTO.lastName());
		String email = this.cryptoUtils.encrypt(userDTO.email());
		
		var encryptedUser = new UserDTO(cpf, userDTO.password(), name, lastName, email, userDTO.address());
	     
	     return encryptedUser;
	     
	}
}
