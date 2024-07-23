package com.ms.stores.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ms.stores.model.menu.MenuModel;

@Repository
public interface MenuRepository extends MongoRepository<MenuModel, String> {

}
