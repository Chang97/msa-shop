package com.msashop.auth.contexts.authr.menu.domain.view;

import java.util.List;

public record MenuTree(
    Long menuId,
    Long upperMenuId,
    String menuCode,
    String menuName,
    String menuCn,
    String url,
    Integer srt,
    Boolean useYn,
    Integer lvl,
    List<MenuTree> children
) {}
