package com.ms.products.exceptions;

public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProductNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProductNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
