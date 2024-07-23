package com.ms.orders.model.order;

import java.time.LocalDateTime;

import com.ms.orders.model.address.AddressDTO;

public record OrderDTO(String id, String userId, String storeId, LocalDateTime orderDate, OrderStatus orderStatus,
		String subtotal, AddressDTO addressDTO) {
}