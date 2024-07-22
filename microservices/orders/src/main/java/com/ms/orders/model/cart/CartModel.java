package com.ms.orders.model.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class CartModel {
	@Id
	private String id;
	private String userId;
	private List<String> productsId;
	private boolean active;




	public CartModel() {
		this.productsId = new ArrayList<>();
	}
	
	
	public CartModel( String userId) {
		this.userId = userId;
		this.active = true;
		this.productsId = new ArrayList<>();;
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

	public List<String> getProductsId() {
		return productsId;
	}

	public void setProductsId(List<String> productsId) {
		this.productsId = productsId;
	}

}
