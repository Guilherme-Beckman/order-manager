package com.ms.stores.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.stores.model.store.StoreModel;

@Repository
public interface StoreRepository extends MongoRepository<StoreModel, String> {
	StoreModel findByEmail(String email);
}
