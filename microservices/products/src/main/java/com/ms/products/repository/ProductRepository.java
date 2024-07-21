package com.ms.products.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.products.model.product.ProductModel;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String>{
	  List<ProductModel> findByOwnerid(String ownerId);

	List<ProductModel> findByMenuId(String menuId);
}
