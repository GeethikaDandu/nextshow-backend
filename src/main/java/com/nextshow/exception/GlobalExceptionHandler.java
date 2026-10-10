package com.nextshow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(AuthenticationException.class)
public ResponseEntity<Map<String, String>> handleAuthenticationException(
        AuthenticationException exception) {

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of(
                    "error", exception.getMessage()
            ));
}
    @ExceptionHandler(BookingException.class)
    public ResponseEntity<Map<String, String>> handleBookingException(
            BookingException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", exception.getMessage()));
    }
}
