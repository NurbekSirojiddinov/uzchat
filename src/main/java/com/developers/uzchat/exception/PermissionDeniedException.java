package com.developers.uzchat.exception;


public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException() {}

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}

