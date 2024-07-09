package com.ms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ms.auth.dto.store.StoreDTO;
import com.ms.auth.dto.store.StoreDetails;
import com.ms.auth.service.store.StoreService;

import jakarta.validation.Valid;

@RestController
public class AuthenticationStoreController {
	@Autowired
	private StoreService storeService;

	@PostMapping("/register-store")
	public ResponseEntity<StoreDetails> registerStore(@RequestBody @Valid StoreDTO data){
		var store = this.storeService.registerStore(data);
		return ResponseEntity.ok().body(store);
	}
}
