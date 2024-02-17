package org.alica.api.exceptions;

import lombok.Getter;

@Getter
public class AuthenticateException extends RuntimeException {

    private final String path;
    public AuthenticateException(String message,String path) {
        super(message);
        this.path = path;
    }
}

