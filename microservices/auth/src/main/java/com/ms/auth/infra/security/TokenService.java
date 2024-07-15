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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.auth.exceptions.auth.token.TokenException;
import com.ms.auth.service.TypeOfUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	public String secret;

	
	public String generateToken(
			@NotBlank(message = "login must not be blank") @NotNull(message = "login must not be null") String login, TypeOfUser typeOfUser) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create().
					withIssuer("auth").
					withSubject(login).
					withClaim("typeOfUser", typeOfUser.name()).
					withExpiresAt(generateExpirationDate()).
					sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new TokenException("Error while generating token");
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();
		} catch (JWTVerificationException e) {
			return "";
		}
	}

	public DecodedJWT getTokenInformations(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth").build().verify(token);
		} catch (JWTVerificationException e) {
			return null;
		}
	}

	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

	}

	public String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;
		return authHeader.replace("Bearer ", "");
	}
}
