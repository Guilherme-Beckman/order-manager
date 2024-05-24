

package com.ms.auth.exceptions;

public class CreateMessageToUserDetailsException extends RuntimeException {
    public CreateMessageToUserDetailsException(String message) {
        super(message);
    }

    public CreateMessageToUserDetailsException(String message, Throwable cause) {
        super(message, cause);
    }
}