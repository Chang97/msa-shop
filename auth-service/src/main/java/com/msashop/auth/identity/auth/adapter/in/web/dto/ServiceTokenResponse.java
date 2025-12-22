package com.msashop.auth.identity.auth.adapter.in.web.dto;

public record ServiceTokenResponse(String accessToken, long expiresIn) {
}
