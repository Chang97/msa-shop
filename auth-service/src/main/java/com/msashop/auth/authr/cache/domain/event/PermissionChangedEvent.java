package com.msashop.auth.authr.cache.domain.event;

import java.util.Collection;

public record PermissionChangedEvent(
    Collection<Long> affectedUserIds
) { }