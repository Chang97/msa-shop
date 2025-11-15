package com.msashop.auth.contexts.authr.role.application.query.dto;

public record RoleQuery(
        Long roleId,
        String roleName,
        Boolean useYn
) {}
