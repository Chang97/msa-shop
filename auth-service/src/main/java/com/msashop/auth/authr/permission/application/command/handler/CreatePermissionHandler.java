package com.msashop.auth.authr.permission.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.msashop.auth.authr.permission.application.command.dto.PermissionCommand;
import com.msashop.auth.authr.permission.application.command.dto.PermissionCommandResult;
import com.msashop.auth.authr.permission.application.command.mapper.PermissionCommandMapper;
import com.msashop.auth.authr.permission.application.command.port.in.CreatePermissionUseCase;
import com.msashop.auth.authr.permission.domain.model.Permission;
import com.msashop.auth.authr.permission.domain.model.PermissionId;
import com.msashop.auth.authr.permission.domain.port.out.PermissionCommandPort;
import com.msashop.auth.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.msashop.auth.authr.userrolemap.domain.port.out.UserRoleMapCommandPort;
import com.msashop.auth.infrastructure.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreatePermissionHandler implements CreatePermissionUseCase {

    private final PermissionCommandPort permissionCommandPort;
    private final RolePermissionMapCommandPort rolePermissionMapCommandPort;
    private final UserRoleMapCommandPort userRoleMapCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final PermissionCommandMapper permissionCommandMapper;

    @Override
    public PermissionCommandResult handle(PermissionCommand command) {
        if (permissionCommandPort.existsByPermissionCode(command.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + command.permissionCode());
        }

        Permission permission = permissionCommandMapper.toDomain(command);

        Permission saved = permissionCommandPort.save(permission);
        PermissionId savedId = saved.getPermissionId();
        if (savedId != null) {
            evictAuthorityCacheForPermission(savedId.permissionId());
        }
        return permissionCommandMapper.toResult(saved);
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapCommandPort.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapCommandPort.findUserIdsByRoleIds(roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
