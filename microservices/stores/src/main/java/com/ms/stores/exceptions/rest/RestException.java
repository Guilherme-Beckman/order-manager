package com.ms.stores.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class RestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Store Microservice Internal Error");
		return pb;
	}
}
