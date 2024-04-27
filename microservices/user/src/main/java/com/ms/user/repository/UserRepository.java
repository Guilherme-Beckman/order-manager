package com.ms.user.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.user.model.user.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String>{

}
