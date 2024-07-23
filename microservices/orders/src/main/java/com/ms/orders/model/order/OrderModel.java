package com.ms.orders.model.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ms.orders.model.address.AddressDTO;

@Document(collection = "orders")
public class OrderModel {
	@Id
	private String id;
	private String userId;
	private String storeId;
	private LocalDateTime orderData;
	private OrderStatus orderStatus;
	private Integer subtotal;
	private List<Map<String, Integer>> productsId;
	private AddressDTO addressDTO;

	public OrderModel(String id, String userId, String storeId, LocalDateTime orderData, OrderStatus orderStatus,
			Integer subtotal, List<String> productsId, AddressDTO addressDTO) {
		this.id = id;
		this.userId = userId;
		this.storeId = storeId;
		this.orderData = LocalDateTime.now();
		this.orderStatus = OrderStatus.PENDING;
		this.subtotal = subtotal;
		this.productsId = new ArrayList<>();
		this.addressDTO = addressDTO;
	}

	public OrderModel() {
		this.orderData = LocalDateTime.now();
		this.orderStatus = OrderStatus.PENDING;
		this.productsId = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public LocalDateTime getOrderData() {
		return orderData;
	}

	public void setOrderData(LocalDateTime orderData) {
		this.orderData = orderData;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}

	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public List<Map<String, Integer>> getProductsId() {
		return productsId;
	}

	public void setProductsId(List<Map<String, Integer>> productsId) {
		this.productsId = productsId;
	}

}
