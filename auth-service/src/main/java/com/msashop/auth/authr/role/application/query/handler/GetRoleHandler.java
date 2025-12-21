package com.msashop.auth.authr.role.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.authr.role.application.query.dto.RoleQueryResult;
import com.msashop.auth.authr.role.application.query.mapper.RoleQueryMapper;
import com.msashop.auth.authr.role.application.query.port.in.GetRoleUseCase;
import com.msashop.auth.authr.role.domain.port.out.RoleQueryPort;
import com.msashop.auth.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.msashop.auth.authr.rolepermissionmap.domain.port.out.RolePermissionMapQueryPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRoleHandler implements GetRoleUseCase {

    private final RoleQueryPort roleQueryPort;
    private final RoleQueryMapper roleQueryMapper;
    private final RolePermissionMapQueryPort rolePermissionMapQueryPort;

    @Override
    public RoleQueryResult handle(Long roleId) {
        return roleQueryPort.findById(roleId)
                .map(role -> roleQueryMapper.toResult(role,
                        rolePermissionMapQueryPort.findByRoleId(roleId).stream()
                                .map(RolePermissionMap::getPermissionId)
                                .toList()))
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
