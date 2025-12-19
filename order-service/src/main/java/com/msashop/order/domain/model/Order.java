package com.msashop.order.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.msashop.order.domain.model.vo.Address;
import com.msashop.order.domain.model.vo.Money;


public class Order {
    private Long id;
    private String orderNumber;
    private Long userId;
    private OrderStatus status = OrderStatus.CREATED;
    private Money subtotal = new Money(BigDecimal.ZERO);
    private Money discount = new Money(BigDecimal.ZERO);
    private Money shipping = new Money(BigDecimal.ZERO);
    private Money total = new Money(BigDecimal.ZERO);
    private String receiverName;
    private String receiverPhone;
    private Address shippingAddress;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private final List<OrderItem> items = new ArrayList<>();
    private boolean inventoryReserved;

    public Order(Long userId, String receiverName, String receiverPhone, Address addr) {
        this.userId = Objects.requireNonNull(userId);
        this.receiverName = Objects.requireNonNull(receiverName);
        this.receiverPhone = Objects.requireNonNull(receiverPhone);
        this.shippingAddress = Objects.requireNonNull(addr);
    }

    public void addItem(OrderItem item) {
        items.add(Objects.requireNonNull(item));
        recalc();
    }

    public void applyDiscount(Money discount) {
        Objects.requireNonNull(discount);
        if (discount.value().compareTo(subtotal.value()) > 0) {
            throw new IllegalArgumentException("discount must be <= subtotal");
        }
        this.discount = discount;
        recalc();
    }

    public void changeShippingFee(Money shipping) {
        this.shipping = Objects.requireNonNull(shipping);
        recalc();
    }

    public void updateReceiver(String name, String phone) {
        this.receiverName = Objects.requireNonNull(name);
        this.receiverPhone = Objects.requireNonNull(phone);
    }

    public void updateShippingAddress(Address address) {
        this.shippingAddress = Objects.requireNonNull(address);
    }

    public void recalc() {
        var sum = items.stream()
                .map(it -> it.lineAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.subtotal = new Money(sum);
        this.total = new Money(sum.subtract(discount.value()).add(shipping.value()));
    }

    public void toPendingPayment() {
        ensure(status == OrderStatus.CREATED, "CREATED→PENDING_PAYMENT");
        status = OrderStatus.PENDING_PAYMENT;
    }

    public void toPaid() {
        ensure(status == OrderStatus.PENDING_PAYMENT, "PENDING_PAYMENT→PAID");
        status = OrderStatus.PAID;
    }

    public void toFulfilled() {
        ensure(status == OrderStatus.PAID, "PAID→FULFILLED");
        status = OrderStatus.FULFILLED;
    }

    public void cancel() {
        ensure(status == OrderStatus.CREATED || status == OrderStatus.PENDING_PAYMENT, "취소 불가");
        status = OrderStatus.CANCELLED;
    }

    public void markInventoryReserved() {
        if (inventoryReserved) {
            throw new IllegalStateException("Inventory already reserved");
        }
        this.inventoryReserved = true;
    }

    public void releaseInventoryReservation() {
        if (!inventoryReserved) {
            return;
        }
        this.inventoryReserved = false;
    }

    public boolean isInventoryReserved() {
        return inventoryReserved;
    }

    public void restoreStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Money getSubtotal() {
        return subtotal;
    }

    public Money getDiscount() {
        return discount;
    }

    public Money getShipping() {
        return shipping;
    }

    public Money getTotal() {
        return total;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void ensure(boolean cond, String msg) {
        if (!cond) {
            throw new IllegalStateException("Invalid transition: " + msg);
        }
    }
}
