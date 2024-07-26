package com.ms.stores.model.menu;

import java.util.List;

import com.ms.stores.model.products.ProductModel;

public class MenuPerfil {
	private String id;
	private String storeId;
	private String name;
	private List<ProductModel> productIds;

	// Construtor
	public MenuPerfil(String id, String storeId, String name, List<ProductModel> productIds) {
		this.id = id;
		this.storeId = storeId;
		this.name = name;
		this.productIds = productIds;
	}

	// Getters e Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductModel> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<ProductModel> productIds) {
		this.productIds = productIds;
	}
}
