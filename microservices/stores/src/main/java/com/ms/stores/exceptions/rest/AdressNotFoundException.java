package com.ms.stores.exceptions.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;


public class AdressNotFoundException extends RestException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Address Not Found Exception");
		pb.setDetail("Error while trying to find a address");
		return pb;
	}

}
