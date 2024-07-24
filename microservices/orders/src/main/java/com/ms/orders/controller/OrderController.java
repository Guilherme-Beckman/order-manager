package com.ms.orders.controller;

import java.util.List;

import javax.lang.model.element.VariableElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orders.model.order.OrderModel;
import com.ms.orders.model.order.OrderPerfilForStores;
import com.ms.orders.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/make/{addressId}")
	public ResponseEntity<List<OrderModel>> makeAOrder(HttpServletRequest servletRequest,
			@PathVariable String addressId) {
		List<OrderModel> orders = this.orderService.makeOrder(servletRequest, addressId);
		return ResponseEntity.ok().body(orders);
	}

	@GetMapping("/store")
	public ResponseEntity<List<OrderPerfilForStores>> getOrdersStore(HttpServletRequest servletRequest) {
		List<OrderPerfilForStores> storeOrders = this.orderService.getOrdersStore(servletRequest);
		return ResponseEntity.ok().body(storeOrders);
	}

	@PutMapping("/store/status/{orderId}/{statusName}")
	public ResponseEntity<OrderModel> changeOrderStatus(HttpServletRequest httpServletRequest,
			@PathVariable String orderId, @PathVariable String statusName) {
		OrderModel changedOrder= this.orderService.changeOrderStatus(httpServletRequest, orderId, statusName);
		return ResponseEntity.ok().body(changedOrder);
	}
}