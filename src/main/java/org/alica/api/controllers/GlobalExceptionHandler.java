package org.alica.api.controllers;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.alica.api.exceptions.AuthenticateException;
import org.alica.api.exceptions.UpdateObjectException;
import org.hibernate.PropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    private static final String ERRORS = "errors";

    private static final String MESSAGE = "message";

    public Map<String,List<String>> getMapResponse(){
        return new HashMap<>();
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,List<String>>> httpParseError(HttpMessageNotReadableException ex){
        HashMap<String, List<String>> body = new HashMap<>();
        List<String> errors = List.of(ex.getMessage());
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<HashMap<String,List<String>>> propertyNotFound(PropertyNotFoundException ex){
        HashMap<String, List<String>> body = new HashMap<>();
        List<String> errors = List.of(ex.getMessage());
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,List<String>>> entityNotFound(EntityNotFoundException ex){
        Map<String, List<String>> body = getMapResponse();
        List<String> errors = List.of(ex.getMessage());
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String,List<String>>> noResourceFound(NoResourceFoundException ex){
        Map<String, List<String>> body = getMapResponse();
        List<String> errors = List.of(ex.getMessage());
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String,List<String>>> methodNotSupported(HttpRequestMethodNotSupportedException ex){
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = List.of(ex.getMessage());
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body,HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String,List<String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult()
                .getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            errors.add(defaultMessage);
        }

        body.put(ERRORS, errors);
        body.put("path", List.of(request.getContextPath()));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getName() + " should be of type " + ex.getRequiredType());
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticateException.class)
    protected ResponseEntity<Object> handleAuthenticateException(
            AuthenticateException ex) {

        Map<String, String> body = new HashMap<>();
        body.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        body.put("error", "Unauthorized");
        body.put(MESSAGE, ex.getMessage());
        body.put("path", ex.getPath());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UpdateObjectException.class)
    protected ResponseEntity<Object> handleUpdateObjectException(
            UpdateObjectException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> handleJMException(
            JwtException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("jwt exception message", ex.getMessage());
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
