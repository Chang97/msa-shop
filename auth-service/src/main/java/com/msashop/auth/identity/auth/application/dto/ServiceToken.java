package com.msashop.auth.identity.auth.application.dto;

public record ServiceToken(String accessToken, long expiresIn) {
}
