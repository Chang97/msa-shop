package com.msashop.auth.contexts.authr.menu.domain.view;

import java.util.List;

public record MenuViews(
    List<MenuTree> tree, 
    List<FlatMenuView> flat
) {}

