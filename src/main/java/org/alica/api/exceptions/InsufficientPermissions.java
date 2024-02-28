package org.alica.api.exceptions;

public class InsufficientPermissions extends RuntimeException {

    public InsufficientPermissions(String message) {
        super(message);
    }
}
