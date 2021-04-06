package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class TagNotFoundException extends ServiceException {

    public TagNotFoundException() {
        super();
    }

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(Throwable e) {
        super(e);
    }

    public TagNotFoundException(String message, Throwable e) {
        super(message, e);
    }

}
