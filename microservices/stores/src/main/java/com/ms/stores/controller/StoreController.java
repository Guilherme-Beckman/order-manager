package com.ms.stores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.products.model.product.ProductModel;
import com.ms.stores.model.products.ProductDTO;
import com.ms.stores.service.StoreService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StoreController {
	@Autowired
	private StoreService storeService;
	@PostMapping("/addProduct")
	public ResponseEntity<ProductModel> addNewProduct(@RequestBody ProductDTO productDTO, HttpServletRequest httpRequest){
		var newProduct = this.storeService.addNewProduct(productDTO, httpRequest);
		return ResponseEntity.ok().body(newProduct);
	}
	@GetMapping("/perfil")
	public ResponseEntity<StorePerfil> getStorePerfil(HttpServletRequest request){
		StorePerfil storePerfil = this.storeService.getStorePerfil(request);
		return ResponseEntity.ok().body(storePerfil);
	}
}
