package org.alica.api.controller;
import jakarta.persistence.EntityNotFoundException;
import org.alica.api.exception.AuthenticateException;
import org.alica.api.exception.UpdateObjectException;
import org.hibernate.PropertyNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    public Map<String,List<String>> getMapResponse(){
        return new HashMap<String,List<String>>();
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,List<String>>> HttpParseError(HttpMessageNotReadableException ex){
        HashMap<String, List<String>> body = new HashMap<>();
        List<String> errors = List.of(ex.getMessage());
        body.put("errors", errors);
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<HashMap<String,List<String>>> PropertyNotFound(PropertyNotFoundException ex){
        HashMap<String, List<String>> body = new HashMap<>();
        List<String> errors = List.of(ex.getMessage());
        body.put("errors", errors);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,List<String>>> EntityNotFound(EntityNotFoundException ex){
        Map<String, List<String>> body = getMapResponse();
        List<String> errors = List.of(ex.getMessage());
        body.put("errors", errors);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String,List<String>>> NoResourceFound(NoResourceFoundException ex){
        Map<String, List<String>> body = getMapResponse();
        List<String> errors = List.of(ex.getMessage());
        body.put("errors", errors);
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String,List<String>>> MethodNotSupported(HttpRequestMethodNotSupportedException ex){
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = List.of(ex.getMessage());
        body.put("errors", errors);
        return new ResponseEntity<>(body,HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String,List<String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getName() + " should be of type " + ex.getRequiredType());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticateException.class)
    protected ResponseEntity<Object> handleAuthenticateException(
            AuthenticateException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UpdateObjectException.class)
    protected ResponseEntity<Object> handleUpdateObjectException(
            UpdateObjectException ex, WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_MODIFIED);
    }

}
