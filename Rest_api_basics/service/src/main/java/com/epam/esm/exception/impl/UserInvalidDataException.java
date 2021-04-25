package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class UserInvalidDataException extends ServiceException {

    public UserInvalidDataException() {
        super();
    }

    public UserInvalidDataException(String message) {
        super(message);
    }

    public UserInvalidDataException(Throwable e) {
        super(e);
    }

    public UserInvalidDataException(String message, Throwable e) {
        super(message, e);
    }
}
