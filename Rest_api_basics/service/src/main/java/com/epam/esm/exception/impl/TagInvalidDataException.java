package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class TagInvalidDataException extends ServiceException {

    public TagInvalidDataException() {
        super();
    }

    public TagInvalidDataException(String message) {
        super(message);
    }

    public TagInvalidDataException(Throwable e) {
        super(e);
    }

    public TagInvalidDataException(String message, Throwable e) {
        super(message, e);
    }
}
