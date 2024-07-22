package com.ms.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.orders.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

//	public OrderModel makeOrder(OrderDTO orderDTO, HttpServletResponse servletResponse) {
//		
//	}

}
