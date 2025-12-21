package com.msashop.auth.infrastructure.exception;

public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message);
    }
}
