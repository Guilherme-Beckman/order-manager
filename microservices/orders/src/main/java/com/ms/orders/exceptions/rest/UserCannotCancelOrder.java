package com.ms.orders.exceptions.rest;

import com.ms.orders.model.order.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserCannotCancelOrder extends RestException {
	private static final long serialVersionUID = 1L;
	private OrderStatus orderStatus;

	public UserCannotCancelOrder(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		pb.setTitle("Order Cancellation Error");
		pb.setDetail("The user is not allowed to cancel the order with status: " + orderStatus);
		return pb;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
}
