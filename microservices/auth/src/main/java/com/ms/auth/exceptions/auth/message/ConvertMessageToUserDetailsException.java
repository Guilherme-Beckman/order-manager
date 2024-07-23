package com.ms.auth.exceptions.auth.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class ConvertMessageToUserDetailsException extends AuthException {
	private static final long serialVersionUID = 1L;
	private String detail;

	public ConvertMessageToUserDetailsException(String detail) {
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