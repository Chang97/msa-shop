package com.msashop.auth.authr.permission.domain.model;

public record PermissionSnapshot(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
