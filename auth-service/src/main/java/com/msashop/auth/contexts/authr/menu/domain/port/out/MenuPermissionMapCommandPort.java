package com.msashop.auth.contexts.authr.menu.domain.port.out;

import java.util.Collection;

import com.msashop.auth.contexts.authr.menu.domain.model.MenuPermissionMap;

public interface MenuPermissionMapCommandPort {

    void replacePermissions(Long menuId, Collection<MenuPermissionMap> permissions);
}
