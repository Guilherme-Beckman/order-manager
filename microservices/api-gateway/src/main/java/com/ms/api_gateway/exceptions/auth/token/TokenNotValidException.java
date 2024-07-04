package com.ms.api_gateway.exceptions.auth.token;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.api_gateway.exceptions.auth.AuthException;

public class TokenNotValidException extends AuthException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		pb.setTitle("Token not valid");
		pb.setDetail("Error while validate the code");
		return pb;
	}

}
