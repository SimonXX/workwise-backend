package com.workwise.workwisebackend.support.exception;

import com.workwise.workwisebackend.support.request.ErrorResponse;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

//usare questa classe annotata con @RestControllerAdvice consente di non dover fare il catch delle eccezioni
//dobbiamo solo mettede il throw new e poi mettere il throw nella signature del metodo (è importante farlo)
@RestControllerAdvice
public class GlobalExceptionHandler{



    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Invalid JSON syntax"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HandlerMethodValidationException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Invalid input data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), "Authentication failed"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.FORBIDDEN.value(), "Access denied"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidConfigurationPropertyValueException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(InvalidConfigurationPropertyValueException ex) {
        String errorMessage = "Resource not found";
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorMessage += ": " + ex.getMessage();
        }
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "Data integrity violation: " + ex.getMostSpecificCause().getMessage();
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.CONFLICT.value(), message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}