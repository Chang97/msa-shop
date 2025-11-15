package com.msashop.auth.contexts.authr.permission.application.command.port.in;

import com.msashop.auth.contexts.authr.permission.application.command.dto.PermissionCommand;
import com.msashop.auth.contexts.authr.permission.application.command.dto.PermissionCommandResult;

public interface CreatePermissionUseCase {

    PermissionCommandResult handle(PermissionCommand command);
}
