package com.msashop.auth.identity.auth.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ServiceTokenRequest(
        @NotBlank
        String clientId,
        @NotBlank
        String clientSecret
) {
}
