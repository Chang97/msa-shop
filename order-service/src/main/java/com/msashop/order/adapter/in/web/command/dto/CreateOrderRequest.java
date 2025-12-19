package com.msashop.order.adapter.in.web.command.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        String receiverName,
        String receiverPhone,
        String postcode,
        String address1,
        String address2,
        List<Item> items
) {
    public record Item(long productId, String productName, BigDecimal unitPrice, int qty) {}
}