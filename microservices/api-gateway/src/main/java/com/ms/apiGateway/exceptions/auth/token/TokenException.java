package com.ms.apiGateway.exceptions.auth.token;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.apiGateway.exceptions.auth.AuthException;

public class TokenException extends AuthException{
	private static final long serialVersionUID = 1L;
	private String detail;
	public TokenException(String detail) {
		this.detail = detail;
	}
	public TokenException(String detail, Throwable cause) {
		this.detail = detail + "caused by"+ cause;
	}
	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		pb.setTitle("Problem while processing token");
		pb.setDetail(detail);
		return pb;
	}
	
	
}
