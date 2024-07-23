package com.ms.stores.model.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menus")
public class MenuModel {
	@Id
	private String id;
	private String storeId;
	private String name;
	private List<String> productIds;

	public MenuModel() {
		this.productIds = new ArrayList<>();
	}

	public MenuModel(MenuDTO menuDTO) {
		this.name = menuDTO.name();
		this.productIds = new ArrayList<>();
	}

	public MenuModel(String id, String name, String storeId, List<String> productIds) {
		this.id = id;
		this.name = name;
		this.storeId = storeId;
		this.productIds = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

}
