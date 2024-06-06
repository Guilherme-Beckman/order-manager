package com.ms.auth.infra.security;

import java.time.Instant;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ms.auth.exceptions.auth.token.TokenException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	public String secret;
	
	public String generateToken(@NotBlank(message = "login must not be blank") @NotNull(message = "login must not be null") String login) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth")
					.withSubject(login)
					.withExpiresAt(generateExpirationDate())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new TokenException("Error while generating token");
		}
	}
	public String validadeToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();
		} catch (JWTVerificationException e) {
		return "";
		}
	}
	private  Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

	}
}
