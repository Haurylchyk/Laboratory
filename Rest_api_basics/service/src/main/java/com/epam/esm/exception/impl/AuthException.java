package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class AuthException extends ServiceException {

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable e) {
        super(e);
    }

    public AuthException(String message, Throwable e) {
        super(message, e);
    }
}
