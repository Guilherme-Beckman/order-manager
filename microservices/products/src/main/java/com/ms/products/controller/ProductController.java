package com.ms.products.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.products.model.ProductDTO;
import com.ms.products.model.ProductModel;
import com.ms.products.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	@PostMapping("/addProduct")
	public ResponseEntity<ProductModel> addNewProduct(@RequestBody ProductDTO productDTO){
		System.out.println("passou aqui");
		var newProduct = this.productService.addNewProduct(productDTO);
		return ResponseEntity.ok().body(newProduct);
	}
	
}
