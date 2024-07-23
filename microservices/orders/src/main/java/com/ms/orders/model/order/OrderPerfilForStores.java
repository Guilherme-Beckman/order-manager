package com.ms.orders.model.order;

import java.time.LocalDateTime;
import java.util.List;

import com.ms.orders.model.address.AddressDTO;
import com.ms.orders.model.product.ProductPerfil;

public record OrderPerfilForStores(
		String id,
		String userId,
		LocalDateTime orderData,
		OrderStatus orderStatus,
		Integer subtotal,
		List<ProductPerfil> productPerfils,
		AddressDTO addressDTO
		
		) {

}
