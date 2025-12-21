package com.msashop.auth.authr.permission.domain.model;

public record PermissionId(Long permissionId) {

    public static PermissionId of(Long permissionId) {
        return permissionId == null ? null : new PermissionId(permissionId);
    }
}
