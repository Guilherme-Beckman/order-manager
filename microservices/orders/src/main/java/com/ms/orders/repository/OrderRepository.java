package com.ms.orders.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms.orders.model.order.OrderModel;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String> {


    @Query("{ 'storeId': ?0, 'active': true }")
    List<OrderModel> findByStoreIdAndActive(String storeId);
    @Query("{ 'storeId': ?0, 'active': false }")
	List<OrderModel> findByStoreIdAndInactive(String storeId);
}
