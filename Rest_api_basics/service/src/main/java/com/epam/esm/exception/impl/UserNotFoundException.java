package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class UserNotFoundException extends ServiceException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable e) {
        super(e);
    }

    public UserNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}