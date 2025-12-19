package com.msashop.product.infrastructure.exception;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
