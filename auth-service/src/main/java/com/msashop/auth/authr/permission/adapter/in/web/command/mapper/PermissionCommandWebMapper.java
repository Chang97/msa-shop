package com.msashop.auth.authr.permission.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.authr.permission.adapter.in.web.command.dto.PermissionCommandRequest;
import com.msashop.auth.authr.permission.adapter.in.web.command.dto.PermissionCommandResponse;
import com.msashop.auth.authr.permission.application.command.dto.PermissionCommand;
import com.msashop.auth.authr.permission.application.command.dto.PermissionCommandResult;
import com.msashop.auth.shared.core.util.StringNormalizer;

@Component
public class PermissionCommandWebMapper {

    public PermissionCommand toCommand(PermissionCommandRequest request) {
        return new PermissionCommand(
                StringNormalizer.trimToNull(request.permissionCode()),
                StringNormalizer.trimToNull(request.permissionName()),
                request.useYn()
        );
    }

    public PermissionCommandResponse toResponse(PermissionCommandResult result) {
        return new PermissionCommandResponse(result.permissionId());
    }
}
