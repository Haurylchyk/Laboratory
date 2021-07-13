package com.epam.esm.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthException extends AuthenticationException {

    public JwtAuthException(String message) {
        super(message);
    }

    public JwtAuthException(String message, Throwable e) {
        super(message, e);
    }
}
