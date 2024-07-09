package com.ms.auth.service.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.auth.dto.store.StoreDTO;
import com.ms.auth.dto.store.StoreDetails;
import com.ms.auth.exceptions.auth.user.UserDataAlreadyExistsException;


@Service
public class StoreService {
	@Autowired
	private CustomStoreDetailsService customStoreDetailsService;
	public StoreDetails registerStore( StoreDTO data) {
		if (this.customStoreDetailsService.loadUserByUsername(data.email()) != null) {
			throw new UserDataAlreadyExistsException("Email is already registered");
		}
		return null;
	}

}
