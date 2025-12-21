package com.msashop.auth.authr.permission.application.query.dto;

public record PermissionQueryResult(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
