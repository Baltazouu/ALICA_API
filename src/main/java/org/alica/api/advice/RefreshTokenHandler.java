package org.alica.api.advice;

import org.alica.api.exceptions.RefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class RefreshTokenHandler {


    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleTokenRefreshException(RefreshTokenException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
               // ex.getMessage(),
               // request.getDescription(false));
    }
}
