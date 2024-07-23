package com.ms.orders.exceptions.rest.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.orders.exceptions.rest.RestException;

public class ConvertMessageException extends RestException {
	private static final long serialVersionUID = 1L;
	private String detail;

	public ConvertMessageException(String detail) {
		this.detail = detail;
	}

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Error try to convert message to UserDetails ");
		pb.setDetail(detail);
		return pb;
	}

}