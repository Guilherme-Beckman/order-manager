package com.ms.auth.exceptions;

public class CryptographyException extends RuntimeException {
    public CryptographyException(String message) {
        super(message);
    }

    public CryptographyException(String message, Throwable cause) {
        super(message, cause);
    }
}