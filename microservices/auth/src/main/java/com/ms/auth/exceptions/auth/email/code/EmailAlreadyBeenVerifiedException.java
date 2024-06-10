package com.ms.auth.exceptions.auth.email.code;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class EmailAlreadyBeenVerifiedException extends AuthException{

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Error while generation code");
		pb.setDetail("Email already been verified");
		return pb;
	}

}
