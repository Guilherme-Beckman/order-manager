package com.ms.products.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.products.model.ProductDTO;
import com.ms.products.model.ProductModel;
import com.ms.products.repository.ProductRepository;


@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public ProductModel addNewProduct(ProductDTO productDTO) {
		ProductModel productModel = new ProductModel(productDTO);
		return this.productRepository.save(productModel);
	}

	public List<ProductModel> getAllProducts() {
		var allProducts  = this.productRepository.findAll();
		List<ProductModel> availableProducts = new ArrayList<>();
		allProducts.forEach(product -> {
			if (product.isAvaliability()) {
				availableProducts.add(product);
			}
		}
		);
		return availableProducts;
	}
}
