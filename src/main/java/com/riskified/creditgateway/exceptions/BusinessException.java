package com.riskified.creditgateway.exceptions;

public class BusinessException extends Exception {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}
