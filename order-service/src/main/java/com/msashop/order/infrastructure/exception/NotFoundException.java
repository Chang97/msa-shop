package com.msashop.order.infrastructure.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }
}
