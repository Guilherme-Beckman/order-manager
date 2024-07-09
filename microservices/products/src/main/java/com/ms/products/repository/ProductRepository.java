package com.ms.products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.products.model.ProductModel;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String>{

}
