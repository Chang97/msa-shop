package com.msashop.auth.contexts.authr.cache.domain.event;

import java.util.Collection;

public record RoleAuthorityChangedEvent(
    Collection<Long> userIds
) { }