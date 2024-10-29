package com.epam.gym.app.exception;

public class NoEntityPresentException extends RuntimeException {

    public NoEntityPresentException(String message) {
        super(message);
    }

    public NoEntityPresentException(String message, Throwable cause) {
        super(message, cause);
    }
}
