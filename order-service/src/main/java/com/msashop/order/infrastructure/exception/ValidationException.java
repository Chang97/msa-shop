package com.msashop.order.infrastructure.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}