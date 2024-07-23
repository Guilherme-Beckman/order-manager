package com.ms.products.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProductNotFoundException extends RestException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		pb.setTitle("Product Not Found Exception");
		pb.setDetail("Error while trying to find a product");
		return pb;
	}
}
