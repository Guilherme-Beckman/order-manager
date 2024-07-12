package com.ms.stores.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ms.stores.model.StoreModel;

public interface StoreRepository extends MongoRepository<StoreModel, String> {
	StoreModel findByEmail(String email);
}
