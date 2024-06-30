package com.ms.api_gateway.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ms.api_gateway.exceptions.auth.token.TokenNotValidException;


@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	public String secret;


	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();
		} catch (JWTVerificationException e) {
			throw new TokenNotValidException();
		}
	}


}
