package com.msashop.auth.contexts.authr.role.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.msashop.auth.contexts.authr.role.domain.model.Role;
import com.msashop.auth.contexts.authr.role.domain.model.RoleFilter;

public interface RoleQueryPort {

    Optional<Role> findById(Long roleId);

    Optional<Role> findByName(String roleName);

    List<Role> findAllByIds(Collection<Long> roleIds);

    List<Role> search(RoleFilter filter);
}
