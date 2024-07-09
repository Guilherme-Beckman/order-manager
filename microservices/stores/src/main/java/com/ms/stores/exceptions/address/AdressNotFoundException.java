package com.ms.stores.exceptions.address;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.stores.exceptions.auth.AuthException;

public class AdressNotFoundException extends AuthException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Address Not Found Exception");
		pb.setDetail("Error while trying to find a address");
		return pb;
	}

}
