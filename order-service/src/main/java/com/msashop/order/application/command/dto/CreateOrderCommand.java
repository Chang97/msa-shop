package com.msashop.order.application.command.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderCommand (
    long userId, 
    @NotBlank
    String receiverName, 
    String receiverPhone,
    String postcode, 
    String address1, 
    String address2,
    List<Item> items
){
    public record Item(long productId, String productName, BigDecimal unitPrice, int qty) {}
}