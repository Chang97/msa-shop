package com.msashop.auth.authr.cache.domain.event;

import java.util.Collection;

public record RoleAuthorityChangedEvent(
    Collection<Long> userIds
) { }