package com.ms.stores.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class MenuNotFoundException extends RestException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		pb.setTitle("Menu Not Found Exception");
		pb.setDetail("Error while trying to find a menu");
		return pb;
	}
}