package com.msashop.auth.contexts.identity.auth.application.dto;

import java.util.List;

public record AuthSession(
        AuthUserSnapshot user,
        List<String> permissions
) {}
