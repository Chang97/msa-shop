package com.msashop.order.contexts.order.adapter.in.web.command.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeOrderStatusRequest(
        @NotBlank
        String status
) { }
