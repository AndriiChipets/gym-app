package com.epam.gym.app.exception;

public class BruteForceException extends RuntimeException {

    public BruteForceException(String message) {
        super(message);
    }

    public BruteForceException(String message, Throwable cause) {
        super(message, cause);
    }
}
