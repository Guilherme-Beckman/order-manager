package com.ms.auth.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ms.auth.exceptions.auth.AuthException;
import com.ms.auth.exceptions.auth.authenticate.AuthenticateMethodException;
import com.ms.auth.exceptions.jwt.JWTValidationException;
import com.ms.auth.exceptions.valid.ValidationException;


@RestControllerAdvice
public class RestExceptionHandler {
	@Autowired
	private ValidationException validationException;
	@Autowired
	private AuthenticateMethodException authenticateMethodException;
	@Autowired 
	JWTValidationException jwtValidationException;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
     return this.validationException.toProblemDetail(e);
    }

    @ExceptionHandler(AuthException.class)
    public ProblemDetail handleAuthException(AuthException e) {
        return e.toProblemDetail();
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationExecetion(AuthenticationException e) {
    	return this.authenticateMethodException.toProblemDetail(e);
    }

    
}