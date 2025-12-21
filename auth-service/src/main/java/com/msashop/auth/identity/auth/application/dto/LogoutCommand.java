package com.msashop.auth.identity.auth.application.dto;

public record LogoutCommand(
        String refreshToken
) {
}
