package com.ms.user.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AuthException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Auth Microservice Internal Error");
		return pb;
	}
}
