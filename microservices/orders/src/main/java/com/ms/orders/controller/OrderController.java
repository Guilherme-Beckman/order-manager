package com.ms.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orders.service.OrderService;


@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
//	public ResponseEntity<OrderModel> makeAOrder(OrderDTO orderDTO, HttpServletResponse servletResponse){
//		OrderModel order = this.orderService.makeOrder(orderDTO, servletResponse);
//		
//	}
}
