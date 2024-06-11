package com.ms.auth.exceptions.auth.attempts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class ExceededNumberOfAttempts extends AuthException {
	private static final long serialVersionUID = 1L;
	private String detail;
	public ExceededNumberOfAttempts(String detail) {
		this.detail = detail;
	}
	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
		pb.setTitle("You have exceeded the number of attempts in a time");
		pb.setDetail(detail);
		return pb;
	}
	

}
