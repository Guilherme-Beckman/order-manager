package com.ms.stores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.stores.infra.security.CryptoUtils;
import com.ms.stores.infra.security.StoreCryto;
import com.ms.stores.model.Role;
import com.ms.stores.model.StoreDTO;
import com.ms.stores.model.StoreModel;
import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.address.AddressModel;
import com.ms.stores.model.opening_hours.OpeningHoursDTO;
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
	@Autowired
	private OpeningHoursService openingHoursService;
	/*@Autowired
	private TokenService tokenService;*/

	@Transactional
	public StoreModel insertUser(StoreDTO storeDTO) {
		var encryptedStore = this.storeCryto.cryptoStoreData(storeDTO);
		StoreModel newStore = new StoreModel(encryptedStore);
		StoreModel savedStore = this.storeRepository.insert(newStore);
		AddressDTO address = storeDTO.address();
		var storeWithAddress = addAdress(savedStore, address);
		List<OpeningHoursDTO> openingHours = storeDTO.opening_hours();
		var storeWithOpeningHours = addOpeningHours(storeWithAddress, openingHours);
		return this.storeRepository.save(storeWithOpeningHours);
	}

	@Transactional
	public StoreModel addAdress(StoreModel savedStore, AddressDTO address) {
		if (!(address.getZipCode() == null)) {
			address = new AddressDTO(address.getZipCode());
		}
		address.setUserId(savedStore.getId());
		AddressModel addedAddress = this.addressService.insertAddress(address);
		savedStore.setAddressId(addedAddress.getId());
		return savedStore;
	}
	@Transactional
	public StoreModel addOpeningHours(StoreModel savedStore, List<OpeningHoursDTO> openingHoursDTOs) {
		openingHoursDTOs.forEach(data ->{
			var hours = this.openingHoursService.addOpeningHours(savedStore.getId(), data);
			savedStore.getOpening_hours().add(hours.getId());
		});
		savedStore.setOpening_hours(savedStore.getOpening_hours());
		return savedStore;
		
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
		store.getRoles().add(Role.ROLE_VERIFIED_EMAIL);
		store.setRoles(store.getRoles());
		this.storeRepository.save(store);

	}
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

