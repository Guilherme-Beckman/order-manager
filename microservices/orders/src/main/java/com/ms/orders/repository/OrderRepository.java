package com.ms.orders.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms.orders.model.order.OrderModel;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String> {

	
	    List<OrderModel> findByStoreId(String storeId);

}
