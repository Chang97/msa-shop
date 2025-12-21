package com.msashop.auth.authr.menu.domain.port.out;

import java.util.Collection;
import java.util.List;

import com.msashop.auth.authr.menu.domain.model.MenuPermissionMap;

public interface MenuPermissionMapQueryPort {

    List<Long> findPermissionIdsByMenuId(Long menuId);

    List<MenuPermissionMap> findByMenuIds(Collection<Long> menuIds);

    List<Long> findMenuIdsByPermissionId(Long permissionId);
}
