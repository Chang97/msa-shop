package com.msashop.auth.authr.permission.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.msashop.auth.authr.permission.application.command.port.in.DeletePermissionUseCase;
import com.msashop.auth.authr.permission.domain.model.Permission;
import com.msashop.auth.authr.permission.domain.port.out.PermissionCommandPort;
import com.msashop.auth.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.msashop.auth.authr.userrolemap.domain.port.out.UserRoleMapCommandPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeletePermissionHandler implements DeletePermissionUseCase {

    private final PermissionCommandPort permissionCommandPort;
    private final RolePermissionMapCommandPort rolePermissionMapCommandPort;
    private final UserRoleMapCommandPort userRoleMapCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long permissionId) {
        Permission existing = permissionCommandPort.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        existing.disable();
        permissionCommandPort.save(existing);
        evictAuthorityCacheForPermission(existing.getPermissionId().permissionId());
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapCommandPort.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapCommandPort.findUserIdsByRoleIds(roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
