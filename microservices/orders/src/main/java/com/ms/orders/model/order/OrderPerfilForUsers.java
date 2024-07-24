package com.ms.orders.model.order;

import java.time.LocalDateTime;
import java.util.List;

import com.ms.orders.model.address.AddressDTO;
import com.ms.orders.model.product.ProductPerfil;

public record OrderPerfilForUsers(String id, String storeId, LocalDateTime orderData, OrderStatus orderStatus,
		Integer subtotal, List<ProductPerfil> productPerfils, AddressDTO addressDTO

) {

}
