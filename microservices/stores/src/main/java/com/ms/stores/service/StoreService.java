package com.ms.stores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.stores.exceptions.StoreNotFoundException;
import com.ms.stores.infra.security.CryptoUtils;
import com.ms.stores.infra.security.StoreCryto;
import com.ms.stores.model.StoreDTO;
import com.ms.stores.model.StoreModel;
import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.address.AddressModel;
import com.ms.stores.repository.StoreRepository;


@Service
public class StoreService {
	@Autowired
	private StoreRepository storeRepository; 
	@Autowired
	private AddressService addressService;
	@Autowired
	private StoreCryto storeCryto;
	@Autowired
	private CryptoUtils cryptoUtils;
	/*@Autowired
	private TokenService tokenService;*/

	@Transactional
	public StoreModel insertUser(StoreDTO storeDTO) {
		var encryptedStore = this.storeCryto.cryptoStoreData(storeDTO);
		StoreModel newStore = new StoreModel(encryptedStore);
		StoreModel savedStore = this.storeRepository.insert(newStore);
		AddressDTO address = storeDTO.address();
		return addAdress(savedStore.getId(), address);
	}

	@Transactional
	public StoreModel addAdress(String storeId, AddressDTO address) {
		var findedUser = this.storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
		if (!(address.getZipCode() == null)) {
			address = new AddressDTO(address.getZipCode());
		}
		address.setUserId(storeId);
		AddressModel addedAddress = this.addressService.insertAddress(address);
		findedUser.setAddressId(addedAddress.getId());
		return this.storeRepository.save(findedUser);
	}



/*@Transactional
public void addAdress(HttpServletRequest request, AddressDTO address) {
    var user = this.findUserByToken(request);
    String userId = user.getId();
    var findedUser = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    if (address.getZipCode() != null) {
        address = new AddressDTO(address.getZipCode());
    }
    address.setUserId(userId);
    AddressModel addedAddress = this.addressService.insertAddress(address);
    List<String> addressesSavedUser = findedUser.getAddress();
    addressesSavedUser.add(addedAddress.getId());
    findedUser.setAdress(addressesSavedUser);
    this.userRepository.save(findedUser);
}*/

	public StoreModel getStoreById(String id) {
		return this.storeRepository.findById(id).orElseThrow();
	}

	public StoreModel getStoreByEmail(String email) {
		System.out.println("aqui no getStoreByEmail: "+email);
		StoreModel user = storeRepository.findByEmail(email.replace("\"", " ").trim());
		return user;
	}

	public void validateStoreEmail(String email) {
		var emailE = this.cryptoUtils.encrypt(email);
		var store = this.getStoreByEmail(emailE);
		store.setValid(true);
		this.storeRepository.save(store);

	}

	/*public UserPerfilDTO getUserPerfil(HttpServletRequest request) {
		var user = this.findUserByToken(request);
		var decryptedUserPerfil = this.userCrypto.decryptUserData(user);

		return decryptedUserPerfil;
	}

	private UserModel findUserByToken(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		var email = userInfos.getSubject();
		var encryptedEmail = this.cryptoUtils.encrypt(email);
		var user = this.getUserByEmail(encryptedEmail);
		return user;
	}
    public void changePassword(String email, String newPassword) {
        var user = this.getUserByEmail(this.cryptoUtils.encrypt(email));
        if (user == null) {
            return; 
        }
        user.setPassword(newPassword);
        this.userRepository.save(user);
    }*/
}
