package com.msashop.auth.contexts.authr.permission.application.query.port.in;

import com.msashop.auth.contexts.authr.permission.application.query.dto.PermissionQueryResult;

public interface GetPermissionUseCase {

    PermissionQueryResult handle(Long permissionId);
}
