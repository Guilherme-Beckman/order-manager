package com.ms.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.orders.model.order.OrderDTO;
import com.ms.orders.model.order.OrderModel;
import com.ms.orders.repository.OrderRepository;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

//	public OrderModel makeOrder(OrderDTO orderDTO, HttpServletResponse servletResponse) {
//		
//	}

}
