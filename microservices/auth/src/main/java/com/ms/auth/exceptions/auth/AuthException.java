package com.ms.auth.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AuthException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("User Microservice Internal Error");
		return pb;
	}
}
