package com.msashop.auth.identity.auth.domain.model;

import java.util.List;

public record ServiceAccount(String clientId, String clientSecret, List<String> permissions) {

    public List<String> permissionsOrEmpty() {
        return permissions == null ? List.of() : permissions;
    }
}
