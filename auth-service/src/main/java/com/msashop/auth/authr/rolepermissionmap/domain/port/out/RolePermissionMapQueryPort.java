package com.msashop.auth.authr.rolepermissionmap.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.msashop.auth.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.msashop.auth.authr.rolepermissionmap.domain.model.RolePermissionMapId;

public interface RolePermissionMapQueryPort {

    Optional<RolePermissionMap> findById(RolePermissionMapId id);

    List<RolePermissionMap> findByRoleId(Long roleId);

    List<RolePermissionMap> findByPermissionId(Long permissionId);

    List<RolePermissionMap> findByRoleIds(Collection<Long> roleIds);
}
