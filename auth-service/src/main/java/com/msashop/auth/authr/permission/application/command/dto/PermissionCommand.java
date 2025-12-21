package com.msashop.auth.authr.permission.application.command.dto;

public record PermissionCommand(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
