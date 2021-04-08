package com.epam.esm.exception;

import com.epam.esm.constant.ErrorMessageKey;
import com.epam.esm.exception.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

import static com.epam.esm.constant.ErrorMessageKey.*;

@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public CommonExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(GiftCertificateInvalidDataException.class)
    public ResponseEntity<ExceptionDescription> processGiftCertificateInvalidDataException(
            GiftCertificateInvalidDataException e, Locale locale) {

        String errorMessage = messageSource.getMessage(INVALID_DATA, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TagInvalidDataException.class)
    public ResponseEntity<ExceptionDescription> processTagInvalidDataException(
            TagInvalidDataException e, Locale locale) {

        String errorMessage = messageSource.getMessage(INVALID_DATA, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<ExceptionDescription> processGiftCertificateNotFoundException(
            GiftCertificateNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ErrorMessageKey.GIFT_CERTIFICATE_NOT_FOUND, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ExceptionDescription> processTagNotFoundException(TagNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ErrorMessageKey.TAG_NOT_FOUND, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingTagException.class)
    public ResponseEntity<ExceptionDescription> processExistingTagException(ExistingTagException e, Locale locale) {

        String errorMessage = messageSource.getMessage(TAG_EXISTS, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDescription> processIllegalArgumentException(IllegalArgumentException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage);
        return new ResponseEntity<>(exceptionDescription, HttpStatus.BAD_REQUEST);
    }
}