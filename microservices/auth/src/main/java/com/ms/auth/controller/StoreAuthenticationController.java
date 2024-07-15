package com.ms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.auth.dto.clients.AuthenticationDTO;
import com.ms.auth.dto.clients.LoginResponseDTO;
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
		var store = this.storeService.registerStore(data);
		return ResponseEntity.ok().body(store);
	}
	@PostMapping("/login-store")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		System.out.println("loja fazendo login");
		var token = this.storeService.storeLogin(data);
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	@GetMapping("/products")
	public ResponseEntity<String> products(){
		return ResponseEntity.ok().body("Acesso certo");
	}
}
