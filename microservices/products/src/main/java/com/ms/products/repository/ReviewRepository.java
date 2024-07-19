package com.ms.products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ms.products.model.reviews.ReviewModel;

public interface ReviewRepository extends MongoRepository<ReviewModel, String>{

}
