package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class ExistingTagException extends ServiceException {

    public ExistingTagException() {
        super();
    }

    public ExistingTagException(String message) {
        super(message);
    }

    public ExistingTagException(Throwable e) {
        super(e);
    }

    public ExistingTagException(String message, Throwable e) {
        super(message, e);
    }

}
