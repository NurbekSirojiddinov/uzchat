package com.developers.uzchat.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthException extends RuntimeException {

    public AuthException() {}

    public AuthException(final String message) {
        super(message);
    }

    public AuthException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public AuthException(final HttpStatus status, final String message) {
        super(message + " Reason: " + status.getReasonPhrase());
    }

    public AuthException(final HttpStatus status, final String message, final Throwable throwable) {
        super(message + " Reason: " + status.getReasonPhrase(), throwable);
    }
}
