package com.msashop.auth.authr.menu.application.query.dto;

import java.util.List;

public record UserMenuAccessResult(
    List<MenuTreeResult> menuTree,
    List<MenuQueryResult> flatMenus
) {}
