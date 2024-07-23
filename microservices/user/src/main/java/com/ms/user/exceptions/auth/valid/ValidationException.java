package com.ms.user.exceptions.auth.valid;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ValidationException {
	public ProblemDetail toProblemDetail(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
		pb.setTitle("Validation Error");
		String detail = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(", "));
		pb.setDetail(detail);
		return pb;
	}
}
