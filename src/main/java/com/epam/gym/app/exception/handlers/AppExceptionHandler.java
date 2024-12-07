package com.epam.gym.app.exception.handlers;

import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.exception.UnsatisfiedActionException;
import com.epam.gym.app.exception.UserNotAuthenticatedException;
import com.epam.gym.app.exception.UserNotLoginException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
@Log4j2
public class AppExceptionHandler {

    private static final String NOT_FOUND_ENTITY = "NO_ENTITY_PRESENT";
    private static final String UNSATISFIED_ACTION = "UNSATISFIED_ACTION";
    private static final String NOT_LOGIN = "NOT_LOGIN";
    private static final String AUTH_REQUIRED = "AUTH_REQUIRED";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(NoEntityPresentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleNoEntityPresentException(NoEntityPresentException ex) {
        return getErrorResponse(NOT_FOUND_ENTITY, ex);
    }

    @ExceptionHandler(UserNotLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleUserNotLoginException(UserNotLoginException ex) {
        return getErrorResponse(NOT_LOGIN, ex);
    }

    @ExceptionHandler(UnsatisfiedActionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleUnsatisfiedActionException(UnsatisfiedActionException ex) {
        return getErrorResponse(UNSATISFIED_ACTION, ex);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ErrorResponse handleUserNotAuthenticatedException(UserNotAuthenticatedException ex) {
        return getErrorResponse(AUTH_REQUIRED, ex);
    }

    private ErrorResponse getErrorResponse(String message, Throwable ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        log.error(String.format("Error: %s, cause: %s", message, Arrays.toString(details.toArray())));

        return new ErrorResponse(message, details);
    }
}
