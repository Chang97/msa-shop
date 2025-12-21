package com.msashop.auth.authr.menu.domain.model;

public record MenuId(Long value) {

    public static MenuId of(Long value) {
        return value == null ? null : new MenuId(value);
    }
}
