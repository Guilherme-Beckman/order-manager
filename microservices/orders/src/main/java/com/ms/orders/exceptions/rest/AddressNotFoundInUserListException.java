package com.ms.orders.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AddressNotFoundInUserListException extends RestException {
	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		pb.setTitle("Address Not Found in User List");
		pb.setDetail("The address was not found in the user's address list.");
		return pb;
	}
}
