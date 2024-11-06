package com.epam.gym.app.exception;

public class UserNotLoginException extends RuntimeException {

    public UserNotLoginException(String message) {
        super(message);
    }

    public UserNotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
