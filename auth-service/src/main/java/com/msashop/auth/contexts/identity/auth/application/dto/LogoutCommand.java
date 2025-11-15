package com.msashop.auth.contexts.identity.auth.application.dto;

public record LogoutCommand(
        String refreshToken
) {
}
