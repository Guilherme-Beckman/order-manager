package com.ms.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orders.model.order.OrderModel;
import com.ms.orders.model.order.OrderPerfilForStores;
import com.ms.orders.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/store")
public class StoreOrderController {
	@Autowired
	private OrderService orderService;
	@GetMapping
	public ResponseEntity<List<OrderPerfilForStores>> getOrdersStore(HttpServletRequest servletRequest) {
		List<OrderPerfilForStores> storeOrders = this.orderService.getOrdersStore(servletRequest);
		return ResponseEntity.ok().body(storeOrders);
	}

	@PutMapping("/status/{orderId}/{statusName}")
	public ResponseEntity<OrderModel> changeOrderStatus(HttpServletRequest httpServletRequest,
			@PathVariable String orderId, @PathVariable String statusName) {
		OrderModel changedOrder = this.orderService.changeOrderStatus(httpServletRequest, orderId, statusName);
		return ResponseEntity.ok().body(changedOrder);
	}
	@GetMapping("/history")
	public ResponseEntity<List<OrderModel>> orderHistory(HttpServletRequest httpServletRequest){
		List<OrderModel> history = this.orderService.getOrderHistory(httpServletRequest);
		return ResponseEntity.ok().body(history);
	}
}
