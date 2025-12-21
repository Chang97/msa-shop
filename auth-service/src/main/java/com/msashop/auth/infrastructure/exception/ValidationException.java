package com.msashop.auth.infrastructure.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}