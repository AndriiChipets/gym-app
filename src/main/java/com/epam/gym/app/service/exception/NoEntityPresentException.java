package com.epam.gym.app.service.exception;

public class NoEntityPresentException extends RuntimeException {

    public NoEntityPresentException(String message) {
        super(message);
    }

    public NoEntityPresentException(String message, Throwable cause) {
        super(message, cause);
    }
}
