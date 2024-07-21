package com.ms.products.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ms.products.exceptions.rest.ProductNotFoundException;
import com.ms.products.model.product.ProductDTO;
import com.ms.products.model.product.ProductModel;
import com.ms.products.model.product.ProductPerfil;
import com.ms.products.model.reviews.ReviewDTO;
import com.ms.products.rabbitMQ.listener.MenuProductDTO;
import com.ms.products.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	@Lazy
	private ReviewService reviewService;

	public ProductModel addNewProduct(ProductDTO productDTO) {
		ProductModel productModel = new ProductModel(productDTO);
		return this.productRepository.insert(productModel);
	}

	public List<ProductModel> getAllProducts() {
		var allProducts = this.productRepository.findAll();
		List<ProductModel> availableProducts = new ArrayList<>();
		allProducts.forEach(product -> {
			if (product.isAvaliability()) {
				availableProducts.add(product);
			}
		});
		return availableProducts;
	}

	public ProductModel saveNewProduct(ProductModel productModel) {
		return this.productRepository.save(productModel);
	}

	public ProductModel getProductById(String id) {
		return this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
	}

	public ProductPerfil getProductPerfilById(String id) {
	    var product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
	    List<ReviewDTO> reviewDTOs = new ArrayList<>();
	    
	    product.getReviews().forEach(review->{
	    	var reviewDTO = this.reviewService.getReviewDTOById(review);
	    	reviewDTOs.add(reviewDTO);
	    });
	    
	    return new ProductPerfil(
	            product.getId(),
	            product.getOwnerid(),
	            product.getName(),
	            product.getPrice(),
	            product.getMenuId(),
	            product.getDescription(),
	            product.getRating(),
	            product.getReviewsCount(),
	            reviewDTOs
	            );
	}
	
	public List<ProductModel> findByStoreId (String storeId){
		return this.productRepository.findByOwnerid(storeId);
	}

	public ProductModel addProductMenu(MenuProductDTO menuProductDTO) {
		var product  = this.getProductById(menuProductDTO.productId());
		product.setMenuId(menuProductDTO.menuId());
		this.productRepository.save(product);
		return product;
	}
}
	

