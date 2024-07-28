package com.ms.auth.exceptions.auth.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class UserDataAlreadyExistsException extends AuthException {
	private static final long serialVersionUID = 1L;

	private String detail;

	public UserDataAlreadyExistsException(String detail) {
		this.detail = detail;
	}

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		pb.setTitle("User data already exists");
		pb.setDetail(detail);
		return pb;
	}
}
