package com.epam.gym.app.exception;

public class UnsatisfiedActionException extends RuntimeException {

    public UnsatisfiedActionException(String message) {
        super(message);
    }

    public UnsatisfiedActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
