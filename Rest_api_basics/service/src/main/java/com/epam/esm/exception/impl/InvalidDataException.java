package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class InvalidDataException extends ServiceException {

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(Throwable e) {
        super(e);
    }

    public InvalidDataException(String message, Throwable e) {
        super(message, e);
    }
}
