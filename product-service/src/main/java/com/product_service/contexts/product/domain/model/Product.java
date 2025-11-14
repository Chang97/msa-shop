package com.product_service.contexts.product.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.product_service.contexts.product.domain.model.exception.InvalidProductStateException;

public class Product {

    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final int stock;
    private final OffsetDateTime createdAt;

    private Product(Long id, String name, BigDecimal price, int stock, OffsetDateTime createdAt) {
        if (name == null || name.isBlank()) {
            throw new InvalidProductStateException("name must not be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductStateException("price must be greater than or equal to 0");
        }
        if (stock < 0) {
            throw new InvalidProductStateException("stock must be greater than or equal to 0");
        }
        this.id = id;
        this.name = name.trim();
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }

    public static Product create(String name, BigDecimal price, int stock) {
        return new Product(null, name, price, stock, null);
    }

    public static Product restore(Long id, String name, BigDecimal price, int stock, OffsetDateTime createdAt) {
        return new Product(id, name, price, stock, createdAt);
    }

    public Product withId(Long id) {
        return restore(id, this.name, this.price, this.stock, this.createdAt);
    }

    public Product withStock(int newStock) {
        return restore(this.id, this.name, this.price, newStock, this.createdAt);
    }

    public Product withCreatedAt(OffsetDateTime createdAt) {
        return restore(this.id, this.name, this.price, this.stock, createdAt);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
