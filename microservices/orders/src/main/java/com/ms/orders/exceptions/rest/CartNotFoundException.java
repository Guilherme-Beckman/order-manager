package com.ms.orders.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CartNotFoundException extends RestException {
	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		pb.setTitle("Cart Not Found");
		pb.setDetail("The cart was not found.");
		return pb;
	}
}
