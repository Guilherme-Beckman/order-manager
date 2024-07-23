package com.ms.auth.exceptions.auth.authenticate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class InvalideCodeException extends AuthException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		pb.setTitle("Invalide Code Exception");
		pb.setDetail("Invalide code");
		return pb;
	}

}
