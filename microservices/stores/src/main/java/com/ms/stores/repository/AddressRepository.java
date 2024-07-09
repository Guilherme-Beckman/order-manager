package com.ms.stores.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.stores.model.address.AddressModel;


@Repository
public interface AddressRepository extends MongoRepository<AddressModel, String>{

}
