package com.msashop.auth.identity.auth.application.dto;

import java.util.List;

import org.springframework.http.ResponseCookie;

public record AuthExecutionResult(
        AuthSession session,
        List<ResponseCookie> cookies
) {
}
