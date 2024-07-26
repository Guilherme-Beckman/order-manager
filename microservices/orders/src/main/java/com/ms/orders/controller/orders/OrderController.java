package com.ms.orders.controller.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orders.model.order.OrderModel;
import com.ms.orders.model.order.OrderPerfilForUsers;
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

	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<OrderModel> cancelOrder(HttpServletRequest httpServletRequest, @PathVariable String orderId) {
		OrderModel order = this.orderService.cancelOrder(httpServletRequest, orderId);
		return ResponseEntity.ok().body(order);
	}

	@GetMapping("/client")
	public ResponseEntity<List<OrderPerfilForUsers>> getOrdersUser(HttpServletRequest servletRequest) {
		List<OrderPerfilForUsers> userOrders = this.orderService.getOrdersUser(servletRequest);
		return ResponseEntity.ok().body(userOrders);
	}

	@GetMapping("/client/history")
	public ResponseEntity<List<OrderModel>> getUserOrderHistory(HttpServletRequest httpServletRequest) {
		List<OrderModel> history = this.orderService.getUserOrderHistory(httpServletRequest);
		return ResponseEntity.ok().body(history);
	}

}