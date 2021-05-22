package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class ExistingUserException extends ServiceException {

    public ExistingUserException() {
        super();
    }

    public ExistingUserException(String message) {
        super(message);
    }

    public ExistingUserException(Throwable e) {
        super(e);
    }

    public ExistingUserException(String message, Throwable e) {
        super(message, e);
    }
}
