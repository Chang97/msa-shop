package com.msashop.auth.authr.permission.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.msashop.auth.authr.permission.domain.model.Permission;
import com.msashop.auth.authr.permission.domain.model.PermissionFilter;

public interface PermissionQueryPort {

    Optional<Permission> findById(Long permissionId);

    Optional<Permission> findByPermissionCode(String permissionCode);

    List<Permission> findAllByIds(Collection<Long> permissionIds);

    List<Permission> findAll(PermissionFilter filter);
}
