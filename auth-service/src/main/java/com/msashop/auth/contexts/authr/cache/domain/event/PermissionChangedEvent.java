package com.msashop.auth.contexts.authr.cache.domain.event;

import java.util.Collection;

public record PermissionChangedEvent(
    Collection<Long> affectedUserIds
) { }