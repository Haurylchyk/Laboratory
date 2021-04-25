package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class OrderNotFoundException extends ServiceException {

    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Throwable e) {
        super(e);
    }

    public OrderNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
