package com.ms.orders.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;


public class ProductNotFoundInCartException extends RestException {
    private static final long serialVersionUID = 1L;
    private String productId;

    public ProductNotFoundInCartException(String productId) {
        this.productId = productId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("Product Not Found in Cart");
        pb.setDetail("The product with ID " + productId + " was not found in the cart.");
        return pb;
    }
}
