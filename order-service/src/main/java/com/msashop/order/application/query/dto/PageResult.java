package com.msashop.order.application.query.dto;

import java.util.List;

public record PageResult<T> (
        List<T> content,
        int page,
        int size,
        long totalElements
) {}
