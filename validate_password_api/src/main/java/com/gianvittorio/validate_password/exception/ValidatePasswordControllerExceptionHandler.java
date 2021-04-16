package com.gianvittorio.validate_password.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidatePasswordControllerExceptionHandler {

    @ExceptionHandler(NullPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleNullPasswordException(NullPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder().errorMessage(e.getLocalizedMessage()).build());
    }

    @ExceptionHandler(MalFormedPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMalformedPasswordException(MalFormedPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder().password(e.getPassword()).errorMessage(e.getLocalizedMessage()).build());
    }
}
