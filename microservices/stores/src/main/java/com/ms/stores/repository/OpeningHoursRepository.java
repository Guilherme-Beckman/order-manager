package com.ms.stores.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.stores.model.opening_hours.OpeningHoursModel;

@Repository
public interface OpeningHoursRepository extends MongoRepository<OpeningHoursModel, String>{
	  List<OpeningHoursModel> findByStoreId(String storeId);
}
