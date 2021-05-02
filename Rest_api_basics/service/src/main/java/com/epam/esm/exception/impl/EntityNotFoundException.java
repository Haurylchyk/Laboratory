package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable e) {
        super(e);
    }

    public EntityNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
