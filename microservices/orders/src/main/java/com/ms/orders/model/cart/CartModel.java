package com.ms.orders.model.cart;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class CartModel {
	@Id
	private String id;
	private String userId;
	private Map<String, Map<String, Integer>> storeProductsId;
	private boolean active;
	private Integer subtotal;



	public CartModel() {
		this.storeProductsId = new HashMap<>();
	}

	public CartModel( String userId) {
		this.userId = userId;
		this.active = true;
		this.storeProductsId = new HashMap<>();
		this.subtotal = 0;
	}
	
	
	public Integer getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}



	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Map<String, Map<String, Integer>> getStoreProductsId() {
		return storeProductsId;
	}


	public void setStoreProductsId(Map<String, Map<String, Integer>> storeProductsId) {
		this.storeProductsId = storeProductsId;
	}



}
