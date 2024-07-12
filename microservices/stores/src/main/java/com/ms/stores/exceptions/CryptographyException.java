package com.ms.stores.exceptions;

public class CryptographyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public CryptographyException(String message) {
        super(message);
    }

    public CryptographyException(String message, Throwable cause) {
        super(message, cause);
    }
}