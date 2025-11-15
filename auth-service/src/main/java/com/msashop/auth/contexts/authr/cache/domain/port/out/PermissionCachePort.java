package com.msashop.auth.contexts.authr.cache.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.msashop.auth.contexts.authr.permission.domain.model.PermissionSnapshot;

public interface PermissionCachePort {

    Optional<List<PermissionSnapshot>> get(String permissionName, Boolean useYn);

    void put(String permissionName, Boolean useYn, List<PermissionSnapshot> permissions);

    void evict(String permissionName, Boolean useYn);

    void evictAll();
}
