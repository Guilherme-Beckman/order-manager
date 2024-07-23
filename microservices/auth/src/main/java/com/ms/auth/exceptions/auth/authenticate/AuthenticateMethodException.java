package com.ms.auth.exceptions.auth.authenticate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateMethodException {
	public ProblemDetail toProblemDetail(AuthenticationException e) {
		var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		pb.setTitle("Authentication Unauthorized Error");
		if (e instanceof BadCredentialsException) {
			pb.setDetail("Login or password are incorrect");
		} else if (e instanceof DisabledException) {
			pb.setDetail("This account are disable");
		} else if (e instanceof LockedException) {
			pb.setDetail("This account is locked");
		}
		return pb;

	}
}
