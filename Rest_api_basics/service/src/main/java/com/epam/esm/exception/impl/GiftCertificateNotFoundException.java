package com.epam.esm.exception.impl;

import com.epam.esm.exception.ServiceException;

public class GiftCertificateNotFoundException extends ServiceException {

    public GiftCertificateNotFoundException() {
        super();
    }

    public GiftCertificateNotFoundException(String message) {
        super(message);
    }

    public GiftCertificateNotFoundException(Throwable e) {
        super(e);
    }

    public GiftCertificateNotFoundException(String message, Throwable e) {
        super(message, e);
    }


}