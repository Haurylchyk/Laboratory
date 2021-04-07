package com.epam.esm.exception;

public class ExceptionDescription {

    private final String errorMessage;
    private String errorCode;

    public ExceptionDescription(String message) {
        this.errorMessage = message;
    }

    public ExceptionDescription(String message, String errorCode) {
        this.errorMessage = message;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
