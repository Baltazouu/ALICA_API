package org.alica.api.exception;

public class InsufficientPermissions extends RuntimeException {

    public InsufficientPermissions(String message) {
        super(message);
    }
}
