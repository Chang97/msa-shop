package com.msashop.auth.authr.permission.application.command.port.in;

import com.msashop.auth.authr.permission.application.command.dto.PermissionCommand;
import com.msashop.auth.authr.permission.application.command.dto.PermissionCommandResult;

public interface UpdatePermissionUseCase {

    PermissionCommandResult handle(Long permissionId, PermissionCommand command);
}
