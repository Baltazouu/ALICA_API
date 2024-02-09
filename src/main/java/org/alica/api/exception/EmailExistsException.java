package org.alica.api.exception;

public class EmailExistsException extends RuntimeException{

    public EmailExistsException(String message) {
        super(message);
    }
}
