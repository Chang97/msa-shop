package com.msashop.product.infrastructure.exception;

public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message);
    }
}
