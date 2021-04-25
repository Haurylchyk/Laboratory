package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class OrderInvalidDataException extends ServiceException {

    public OrderInvalidDataException() {
        super();
    }

    public OrderInvalidDataException(String message) {
        super(message);
    }

    public OrderInvalidDataException(Throwable e) {
        super(e);
    }

    public OrderInvalidDataException(String message, Throwable e) {
        super(message, e);
    }


}
