package com.epam.esm.exception;

import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.constant.ErrorMessageKey;
import com.epam.esm.exception.impl.AuthException;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.ExistingTagException;
import com.epam.esm.exception.impl.ExistingUserException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.security.exception.JwtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

import static com.epam.esm.constant.ErrorMessageKey.AUTH_FAILED;
import static com.epam.esm.constant.ErrorMessageKey.ILLEGAL_ARGUMENT;
import static com.epam.esm.constant.ErrorMessageKey.INVALID_DATA;
import static com.epam.esm.constant.ErrorMessageKey.TAG_EXISTS;
import static com.epam.esm.constant.ErrorMessageKey.TOKEN_NOT_VALID;
import static com.epam.esm.constant.ErrorMessageKey.USER_EXISTS;

@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public CommonExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionDescription> processInvalidDataException(
            InvalidDataException e, Locale locale) {

        String errorMessage = messageSource.getMessage(INVALID_DATA, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDescription> processEntityNotFoundException(
            EntityNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ErrorMessageKey.ENTITY_NOT_FOUND, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotExistingPageException.class)
    public ResponseEntity<ExceptionDescription> processNotExistingPageException(
            NotExistingPageException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ErrorMessageKey.PAGE_NOT_EXISTS, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingTagException.class)
    public ResponseEntity<ExceptionDescription> processExistingTagException(ExistingTagException e, Locale locale) {

        String errorMessage = messageSource.getMessage(TAG_EXISTS, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ExceptionDescription> processExistingUserException(ExistingUserException e, Locale locale) {

        String errorMessage = messageSource.getMessage(USER_EXISTS, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDescription> processIllegalArgumentException(IllegalArgumentException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage,
                ErrorCodeMessage.ERROR_CODE_ARG_NOT_VALID);
        return new ResponseEntity<>(exceptionDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDescription> processIllegalArgumentException(ConstraintViolationException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, ErrorCodeMessage.ERROR_CODE_ARG_NOT_VALID);
        return new ResponseEntity<>(exceptionDescription, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage,
                ErrorCodeMessage.ERROR_CODE_ARG_NOT_VALID);
        return handleExceptionInternal(ex, exceptionDescription, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<ExceptionDescription> processJwtAuthException(JwtAuthException e, Locale locale) {

        String errorMessage = messageSource.getMessage(TOKEN_NOT_VALID, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionDescription> processAuthException(AuthException e, Locale locale) {

        String errorMessage = messageSource.getMessage(AUTH_FAILED, new Object[]{}, locale);
        ExceptionDescription exceptionDescription = new ExceptionDescription(errorMessage, e.getMessage());
        return new ResponseEntity<>(exceptionDescription, HttpStatus.NOT_FOUND);
    }
}