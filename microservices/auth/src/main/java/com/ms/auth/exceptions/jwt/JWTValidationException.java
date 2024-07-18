package com.ms.auth.exceptions.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTValidationException {
	@Autowired
	private ObjectMapper objectMapper;

	public ProblemDetail toProblemDetail(JWTVerificationException e, HttpServletResponse response) {
		ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Token Validation failed");
		pb.setTitle("Token Validation Error");
		String detail = e.getMessage();
		pb.setDetail(detail);
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType("application/json");
		String jsonResponse = null;
		try {
			jsonResponse = objectMapper.writeValueAsString(pb);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		try {
			response.getWriter().write(jsonResponse);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return pb;
	}

}
