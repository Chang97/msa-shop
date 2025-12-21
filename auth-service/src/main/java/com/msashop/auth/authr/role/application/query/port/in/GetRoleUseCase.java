package com.msashop.auth.authr.role.application.query.port.in;

import com.msashop.auth.authr.role.application.query.dto.RoleQueryResult;

public interface GetRoleUseCase {

    RoleQueryResult handle(Long roleId);
}
