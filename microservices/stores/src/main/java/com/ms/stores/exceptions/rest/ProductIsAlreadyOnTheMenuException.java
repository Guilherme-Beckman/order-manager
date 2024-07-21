package com.ms.stores.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProductIsAlreadyOnTheMenuException extends RestException {

    private static final long serialVersionUID = 1L;

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pb.setTitle("Product Is Already On The Menu Exception");
        pb.setDetail("The product is already on the menu");
        return pb;
    }
}