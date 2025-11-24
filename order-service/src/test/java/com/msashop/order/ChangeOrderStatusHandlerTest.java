package com.msashop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msashop.order.contexts.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.contexts.order.application.command.handler.ChangeOrderStatusHandler;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderItem;
import com.msashop.order.contexts.order.domain.model.OrderStatus;
import com.msashop.order.contexts.order.domain.model.vo.Address;
import com.msashop.order.contexts.order.domain.model.vo.Money;
import com.msashop.order.contexts.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.contexts.order.domain.service.StatusTransitionResult;
import com.msashop.order.contexts.order.domain.service.StatusTransitionService;
import com.msashop.order.platform.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
class ChangeOrderStatusHandlerTest {

    @Mock OrderRepositoryPort orderRepositoryPort;
    @Mock ProductInventoryPort productInventoryPort;

    ChangeOrderStatusHandler handler;

    @BeforeEach
    void setUp() {
        StatusTransitionService statusTransitionService = new StatusTransitionService(productInventoryPort);
        handler = new ChangeOrderStatusHandler(orderRepositoryPort, statusTransitionService);
    }

    @Test
    void cancelOrder_releasesInventory_whenReserved() {
        Order order = createOrder();
        order.addItem(new OrderItem(2001L, "상품A", new Money(new BigDecimal("1000")), 2));
        order.markInventoryReserved();

        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));

        StatusTransitionResult result = handler.handle(
                new ChangeOrderStatusCommand(1L, OrderStatus.CANCELLED)
        );

        assertThat(result.releaseInventory()).isTrue();
        verify(productInventoryPort).release(2001L, 2);
        verify(orderRepositoryPort).save(order);
    }

    @Test
    void changeStatus_orderNotFound_throwsException() {
        when(orderRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                handler.handle(new ChangeOrderStatusCommand(99L, OrderStatus.CANCELLED))
        );

        verify(productInventoryPort, never()).release(anyLong(), anyInt());
    }

    private Order createOrder() {
        Address address = new Address("12345", "서울시", "강남구");
        return new Order(1L, "홍길동", "010-0000-0000", address);
    }
}
