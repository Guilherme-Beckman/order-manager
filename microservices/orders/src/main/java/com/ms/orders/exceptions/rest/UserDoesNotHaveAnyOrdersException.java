package com.ms.orders.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserDoesNotHaveAnyOrdersException extends RestException {
	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setTitle("Orders Not Found");
		problemDetail.setDetail("Sorry, yout don't have any order"); // Usa a mensagem da exceção
		return problemDetail;
	}

}
