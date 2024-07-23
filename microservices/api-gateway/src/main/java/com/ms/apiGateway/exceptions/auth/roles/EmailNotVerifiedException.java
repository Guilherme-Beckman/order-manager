package com.ms.apiGateway.exceptions.auth.roles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.apiGateway.exceptions.auth.AuthException;

public class EmailNotVerifiedException extends AuthException {

	private static final long serialVersionUID = 1L;

	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		pb.setTitle("Email Not Verified");
		pb.setDetail("This route just allows verified users to acess");
		return pb;
	}

}
