package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class NotExistingPageException extends ServiceException {

    public NotExistingPageException() {
        super();
    }

    public NotExistingPageException(String message) {
        super(message);
    }

    public NotExistingPageException(Throwable e) {
        super(e);
    }

    public NotExistingPageException(String message, Throwable e) {
        super(message, e);
    }
}
