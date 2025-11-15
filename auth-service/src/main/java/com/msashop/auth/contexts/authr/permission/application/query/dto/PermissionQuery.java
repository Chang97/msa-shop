package com.msashop.auth.contexts.authr.permission.application.query.dto;

public record PermissionQuery(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
