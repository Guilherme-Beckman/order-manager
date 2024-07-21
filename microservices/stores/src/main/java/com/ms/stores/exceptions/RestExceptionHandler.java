package com.ms.stores.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ms.stores.exceptions.rest.RestException;
import com.ms.stores.exceptions.valid.ValidationException;


@RestControllerAdvice
public class RestExceptionHandler {
	@Autowired
	private ValidationException validationException;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
     return this.validationException.toProblemDetail(e);
    }

    @ExceptionHandler(RestException.class)
    public ProblemDetail handleAuthException(RestException e) {
        return e.toProblemDetail();
    }
    

    
}