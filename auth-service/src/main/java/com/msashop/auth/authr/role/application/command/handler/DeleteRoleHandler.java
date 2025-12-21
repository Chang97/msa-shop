package com.msashop.auth.authr.role.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.msashop.auth.authr.role.application.command.port.in.DeleteRoleUseCase;
import com.msashop.auth.authr.role.domain.model.Role;
import com.msashop.auth.authr.role.domain.port.out.RoleCommandPort;
import com.msashop.auth.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.msashop.auth.authr.userrolemap.domain.port.out.UserRoleMapCommandPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteRoleHandler implements DeleteRoleUseCase {

    private final RoleCommandPort roleCommandPort;
    private final RolePermissionMapCommandPort rolePermissionMapCommandPort;
    private final UserRoleMapCommandPort userRoleMapCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long roleId) {
        Role existing = roleCommandPort.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existing.disable();
        roleCommandPort.save(existing);

        rolePermissionMapCommandPort.deleteAllByRoleId(roleId);
        List<Long> affectedUsers = userRoleMapCommandPort.findUserIdsByRoleIds(List.of(roleId));
        userRoleMapCommandPort.deleteAllByRoleId(roleId);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(affectedUsers);
        authorityCacheEventPort.publishPermissionsChanged(affectedUsers);
    }
}
