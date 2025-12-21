package com.msashop.auth.authr.role.application.command.dto;

import java.util.List;

public record RoleCommand(
        String roleName,
        Boolean useYn,
        List<Long> permissionIds
) {}
