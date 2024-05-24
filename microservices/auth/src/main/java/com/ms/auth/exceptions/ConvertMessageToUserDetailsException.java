package com.ms.auth.exceptions;

public class ConvertMessageToUserDetailsException extends RuntimeException {
    public ConvertMessageToUserDetailsException(String message) {
        super(message);
    }

    public ConvertMessageToUserDetailsException(String message, Throwable cause) {
        super(message, cause);
    }
}