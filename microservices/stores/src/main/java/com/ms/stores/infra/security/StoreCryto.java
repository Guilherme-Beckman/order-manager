package com.ms.stores.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.stores.model.store.StoreDTO;

@Component
public class StoreCryto {
	@Autowired
	private CryptoUtils cryptoUtils;

	public StoreDTO cryptoStoreData(StoreDTO storeDTO) {

		String CNPJ = this.cryptoUtils.encrypt(storeDTO.CNPJ());
		// String password = this.cryptoUtils.encrypt(userDTO.password());
		String name = this.cryptoUtils.encrypt(storeDTO.name());
		String email = this.cryptoUtils.encrypt(storeDTO.email());
		String phone = this.cryptoUtils.encrypt(storeDTO.phone());
		var encryptedStore = new StoreDTO(name, email, storeDTO.password(), storeDTO.address(), phone, CNPJ, storeDTO.opening_hours());

		return encryptedStore;

	}

	/*public UserPerfilDTO decryptStoreData(StoreModel storeModel) {
		String id = storeModel.getId();
		String name = this.cryptoUtils.decrypt(storeModel.getName());
		String lastname = this.cryptoUtils.decrypt(storeModel.getLastName());
		String email = this.cryptoUtils.decrypt(storeModel.getEmail());
		List<AddressDTO> addressDTOs = this.addressCrypto.decryptAddresses(storeModel.getAddress());
		UserPerfilDTO storePerfilDTO = new storeModel(id, name, lastname, email, addressDTOs);
		return storePerfilDTO;

	}*/
}
