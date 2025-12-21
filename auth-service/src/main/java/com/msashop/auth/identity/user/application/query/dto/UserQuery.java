package com.msashop.auth.identity.user.application.query.dto;

public record UserQuery(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        Long statusCodeId,
        Boolean useYn
) {
}
