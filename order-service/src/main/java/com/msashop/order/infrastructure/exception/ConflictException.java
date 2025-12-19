package com.msashop.order.infrastructure.exception;

public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message);
    }
}
