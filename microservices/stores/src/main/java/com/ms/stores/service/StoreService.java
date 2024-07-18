package com.ms.stores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.products.model.ProductModel;
import com.ms.stores.infra.security.CryptoUtils;
import com.ms.stores.infra.security.StoreCryto;
import com.ms.stores.infra.security.TokenService;
import com.ms.stores.model.Role;
import com.ms.stores.model.address.AddressDTO;
import com.ms.stores.model.address.AddressModel;
import com.ms.stores.model.opening_hours.OpeningHoursDTO;
import com.ms.stores.model.products.ProductDTO;
import com.ms.stores.model.store.StoreDTO;
import com.ms.stores.model.store.StoreModel;
import com.ms.stores.rabbitMQ.producer.AddProductProducer;
import com.ms.stores.repository.StoreRepository;

import jakarta.servlet.http.HttpServletRequest;

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
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AddProductProducer addProductProducer;

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
		openingHoursDTOs.forEach(data -> {
			var hours = this.openingHoursService.addOpeningHours(savedStore.getId(), data);
			savedStore.getOpening_hours().add(hours.getId());
		});
		return savedStore;

	}

	public StoreModel getStoreById(String id) {
		return this.storeRepository.findById(id).orElseThrow();
	}

	public StoreModel getStoreByEmail(String email) {
		System.out.println("email: "+email);
		StoreModel user = storeRepository.findByEmail(email.replace("\"", " ").trim());
		return user;
	}

	public void validateStoreEmail(String email) {
		var emailE = this.cryptoUtils.encrypt(email);
		var store = this.getStoreByEmail(emailE);
		store.getRoles().add(Role.ROLE_VERIFIED_EMAIL);
		this.storeRepository.save(store);

	}

	@Transactional
	public ProductModel addNewProduct(ProductDTO productDTO, HttpServletRequest httpRequest) {
		var token = this.tokenService.recoverToken(httpRequest);
		var storeInfo = this.tokenService.getTokenInformations(token);
		var encrytedStore = this.cryptoUtils.encrypt(storeInfo.getSubject());
		var store  = this.getStoreByEmail(encrytedStore);
		ProductDTO newProductDTO = new ProductDTO(store.getId(), productDTO.name(), productDTO.price(),
				productDTO.description(), productDTO.avaliability(), null);
		
		var addedProduct = this.addProductProducer.addProduct(newProductDTO);
		this.storeRepository.save(this.addProduct(store, addedProduct.getId()));
		return addedProduct;
	}
	public StoreModel addProduct(StoreModel storeModel, String productId) {
		storeModel.getProducts().add(productId);
		return storeModel;
	}
}
