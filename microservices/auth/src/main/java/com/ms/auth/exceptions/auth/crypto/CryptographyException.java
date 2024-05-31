package com.ms.auth.exceptions.auth.crypto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.ms.auth.exceptions.auth.AuthException;

public class CryptographyException extends AuthException {
    private static final long serialVersionUID = 1L;
    private String detail;
	public CryptographyException(String detail) {
        this.detail = detail;
	}
	@Override
	public ProblemDetail toProblemDetail() {
		var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pb.setDetail("Cryptography Error");
		pb.setDetail(detail);
		return pb;
	}
	
}