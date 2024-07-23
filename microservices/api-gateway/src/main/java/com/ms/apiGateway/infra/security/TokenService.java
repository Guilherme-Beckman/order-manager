package com.ms.apiGateway.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.apiGateway.exceptions.auth.token.TokenNotValidException;

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

	public DecodedJWT getTokenInformation(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth").build().verify(token);
		} catch (JWTVerificationException e) {
			throw new TokenNotValidException();
		}
	}

	private TypeOfUser getTypeOfUserFromToken(String token) {
		var jwt = this.getTokenInformation(token);
		String typeOfUserString = jwt.getClaim("typeOfUser").asString();
		return TypeOfUser.valueOf(typeOfUserString);
	}

	public boolean isClient(String token) {
		TypeOfUser typeOfUser = getTypeOfUserFromToken(token);
		return typeOfUser == TypeOfUser.CLIENT;
	}

	public boolean isStore(String token) {
		TypeOfUser typeOfUser = getTypeOfUserFromToken(token);
		return typeOfUser == TypeOfUser.STORE;
	}
}
