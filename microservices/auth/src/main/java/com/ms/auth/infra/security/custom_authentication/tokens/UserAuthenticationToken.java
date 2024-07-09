package com.ms.auth.infra.security.custom_authentication.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	public UserAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}



}
