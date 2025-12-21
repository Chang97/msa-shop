package com.msashop.auth.authr.permission.domain.port.out;

import java.util.List;

public interface PermissionCacheInvalidationPort {

    void invalidateRoleAuthorities(List<Long> userIds);

    void invalidatePermissions(List<Long> userIds);
}
