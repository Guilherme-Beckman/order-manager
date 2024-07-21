package com.ms.stores.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ms.stores.controller.StorePerfil;
import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.store.StoreDTO;
import com.ms.stores.model.store.StoreModel;

@Component
public class StoreCrypto {
	@Autowired
	private CryptoUtils cryptoUtils;
	@Autowired
	private DecryptAddresses decryptAddresses;

	public StoreDTO cryptoStoreData(StoreDTO storeDTO) {
		String CNPJ = this.cryptoUtils.encrypt(storeDTO.CNPJ());
		// String password = this.cryptoUtils.encrypt(userDTO.password());
		String name = this.cryptoUtils.encrypt(storeDTO.name());
		String email = this.cryptoUtils.encrypt(storeDTO.email());
		String phone = this.cryptoUtils.encrypt(storeDTO.phone());
		var encryptedStore = new StoreDTO(name, email, storeDTO.password(), storeDTO.address(), phone, CNPJ,
				storeDTO.opening_hours());

		return encryptedStore;

	}

	public StorePerfil decryptStoreData(StoreModel storeModel) {
		System.out.println(storeModel.getName());
		String name = this.cryptoUtils.decrypt(storeModel.getName());
		System.out.println(storeModel.getPhone());
		String phone = this.cryptoUtils.decrypt(storeModel.getPhone());
		StorePerfil storePerfilDTO = new StorePerfil(storeModel.getId(), name, null, phone,
				null, null);
		return storePerfilDTO;

	}
}
