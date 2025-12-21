package com.msashop.auth.authr.role.application.command.port.in;

import com.msashop.auth.authr.role.application.command.dto.RoleCommand;
import com.msashop.auth.authr.role.application.command.dto.RoleCommandResult;

public interface UpdateRoleUseCase {

    RoleCommandResult handle(Long roleId, RoleCommand command);
}
