package com.msashop.auth.contexts.authr.role.adapter.in.web.query.dto;

public record RoleQueryRequest(
        Long roleId,
        String roleName,
        Boolean useYn
) {}
