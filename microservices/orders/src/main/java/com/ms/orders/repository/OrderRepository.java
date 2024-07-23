package com.ms.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.orders.model.order.OrderModel;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String> {

}
