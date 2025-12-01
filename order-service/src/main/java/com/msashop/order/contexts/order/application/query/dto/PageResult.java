package com.msashop.order.contexts.order.application.query.dto;

import java.util.List;

public record PageResult<T> (
        List<T> content,
        int page,
        int size,
        long totalElements
) {}
