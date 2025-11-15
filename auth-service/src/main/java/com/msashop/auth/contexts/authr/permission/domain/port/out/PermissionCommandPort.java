package com.msashop.auth.contexts.authr.permission.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.msashop.auth.contexts.authr.permission.domain.model.Permission;

public interface PermissionCommandPort {

    Permission save(Permission permission);

    Optional<Permission> findById(Long permissionId);

    Optional<Permission> findByPermission(String permissionCode);

    List<Permission> findAllByIds(Collection<Long> permissionIds);

    Optional<Permission> findByPermissionCode(String permissionCode);

    boolean existsByPermissionCode(String permissionCode);
}
