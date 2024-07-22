package com.ms.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms.orders.model.cart.CartModel;
@Repository
public interface CartRepository extends MongoRepository<CartModel, String>{
	@Query("{'active':true, 'userId': ?0} ")
	CartModel findActiveCartsByUserId(String userId);
}
