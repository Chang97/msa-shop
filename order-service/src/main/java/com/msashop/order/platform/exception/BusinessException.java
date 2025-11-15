package com.msashop.order.platform.exception;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
