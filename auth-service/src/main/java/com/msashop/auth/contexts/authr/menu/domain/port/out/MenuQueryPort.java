package com.msashop.auth.contexts.authr.menu.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.msashop.auth.contexts.authr.menu.domain.model.Menu;
import com.msashop.auth.contexts.authr.menu.domain.model.MenuFilter;

public interface MenuQueryPort {

    Optional<Menu> findById(Long menuId);

    List<Menu> search(MenuFilter filter);

    List<Menu> findAccessibleMenusByUserId(Long userId);
}

