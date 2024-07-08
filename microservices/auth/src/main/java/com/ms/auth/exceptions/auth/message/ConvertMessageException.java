package com.ms.auth.exceptions.auth.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class ConvertMessageException extends AuthException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setTitle("Convert Message Exception");
		pb.setDetail("Error while converting message");
		return pb;
	}

}
