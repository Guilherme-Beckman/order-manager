package com.ms.orders.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class OrderNotFoundException extends RestException {

    private static final long serialVersionUID = 1L;
    private String id;
    private String storeId;
    public OrderNotFoundException(String id, String storeId) {
		this.id = id;
		this.storeId = storeId;
	}
	@Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("Order Not Found");
        pb.setDetail("Order with ID " + id + " not found for store " + storeId);
        return pb;
    }
}
