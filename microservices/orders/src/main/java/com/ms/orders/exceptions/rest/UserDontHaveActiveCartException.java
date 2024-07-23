package com.ms.orders.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserDontHaveActiveCartException extends RestException {
	private static final long serialVersionUID = 1L;
	private String userId; // Campo adicional para informações específicas

	public UserDontHaveActiveCartException(String userId) {
		this.userId = userId;
	}

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		pb.setTitle("Active Cart Not Found");
		pb.setDetail("The user with ID " + userId + " does not have an active cart.");
		return pb;
	}
}
