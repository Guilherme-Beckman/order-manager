package com.ms.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.products.model.ProductModel;
import com.ms.products.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		var allProducts = this.productService.getAllProducts();
		return ResponseEntity.ok().body(allProducts);
	}
}
