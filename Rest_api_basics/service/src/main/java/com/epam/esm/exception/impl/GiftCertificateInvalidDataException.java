package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class GiftCertificateInvalidDataException extends ServiceException {

    public GiftCertificateInvalidDataException() {
        super();
    }

    public GiftCertificateInvalidDataException(String message) {
        super(message);
    }

    public GiftCertificateInvalidDataException(Throwable e) {
        super(e);
    }

    public GiftCertificateInvalidDataException(String message, Throwable e) {
        super(message, e);
    }

}
