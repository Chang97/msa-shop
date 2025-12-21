package com.msashop.auth.authr.role.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.authr.role.adapter.in.web.command.dto.RoleCommandRequest;
import com.msashop.auth.authr.role.adapter.in.web.command.dto.RoleCommandResponse;
import com.msashop.auth.authr.role.application.command.dto.RoleCommand;
import com.msashop.auth.authr.role.application.command.dto.RoleCommandResult;
import com.msashop.auth.shared.core.util.StringNormalizer;
import java.util.List;
import java.util.Objects;

@Component
public class RoleCommandWebMapper {

    public RoleCommand toCommand(RoleCommandRequest request) {
        return new RoleCommand(
                StringNormalizer.trimToNull(request.roleName()),
                request.useYn(),
                sanitizePermissionIds(request.permissionIds())
        );
    }

    public RoleCommandResponse toResponse(RoleCommandResult result) {
        return new RoleCommandResponse(result.roleId());
    }

    private List<Long> sanitizePermissionIds(List<Long> permissionIds) {
        if (permissionIds == null) {
            return List.of();
        }
        return permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }
}
