package com.ms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.auth.dto.store.StoreDTO;
import com.ms.auth.dto.store.StoreDetailsDTO;
import com.ms.auth.service.store.StoreAuthenticationService;

import jakarta.validation.Valid;

@RestController
public class StoreAuthenticationController {
	@Autowired
	private StoreAuthenticationService storeService;

	@PostMapping("/register-store")
	public ResponseEntity<StoreDetailsDTO> registerStore(@RequestBody @Valid StoreDTO data){
		System.out.println("passou pelo controller 1 ");
		var store = this.storeService.registerStore(data);
		return ResponseEntity.ok().body(store);
	}
}
