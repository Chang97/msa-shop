package com.msashop.auth.identity.auth.application.dto;

public record AuthUserSnapshot(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        String empNo,
        String positionName,
        String tel,
        Boolean useYn
) {
}
