package com.msashop.auth.identity.auth.adapter.in.web.dto;

public record UserSummaryResponse(
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
