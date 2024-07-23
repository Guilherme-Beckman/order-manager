package com.ms.user.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ms.user.exceptions.auth.AuthException;
import com.ms.user.exceptions.auth.valid.ValidationException;

@RestControllerAdvice
public class RestExceptionHandler {
	@Autowired
	private ValidationException validationException;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return this.validationException.toProblemDetail(e);
	}

	@ExceptionHandler(AuthException.class)
	public ProblemDetail handleAuthException(AuthException e) {
		return e.toProblemDetail();
	}

}