package com.msashop.order.domain.port.out;

public interface ProductInventoryPort {
    void reserve(long productId, int qty);
    
    void release(long productId, int qty);
}
