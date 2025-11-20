package com.msashop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.contexts.order.application.command.handler.CreateOrderHandler;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.vo.ProductSnapshot;
import com.msashop.order.contexts.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.contexts.order.domain.port.out.OutboxPort;
import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.contexts.order.domain.port.out.ProductQueryPort;
import com.msashop.order.platform.exception.ConflictException;
import com.msashop.order.platform.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
class CreateOrderHandlerTest {

    @Mock OrderRepositoryPort orderRepositoryPort;
    @Mock OutboxPort outboxPort;
    @Mock ProductQueryPort productQueryPort;
    @Mock ProductInventoryPort productInventoryPort;

    CreateOrderHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreateOrderHandler(orderRepositoryPort, outboxPort, productQueryPort, productInventoryPort);
    }

    @Test
    void createOrder_success_reservesInventoryAndSavesOutbox() {
        CreateOrderCommand command = createCommand(
                new CreateOrderCommand.Item(2001L, "상품A", new BigDecimal("35000"), 2)
        );
        ProductSnapshot snapshot = new ProductSnapshot(2001L, "상품A", new BigDecimal("35000"), 10);

        when(productQueryPort.findById(2001L)).thenReturn(Optional.of(snapshot));
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(invocation -> {
            Order saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        long orderId = handler.handle(command);

        assertThat(orderId).isEqualTo(1L);
        verify(productInventoryPort).reserve(2001L, 2);
        verify(orderRepositoryPort).save(any(Order.class));
        verify(outboxPort).savePending(eq("ORDER_CREATED"), eq(1L), any(OrderCreatedEventPayload.class));
    }

    @Test
    void createOrder_productNotFound_throwsException() {
        CreateOrderCommand command = createCommand(
                new CreateOrderCommand.Item(2001L, "상품A", new BigDecimal("35000"), 1)
        );
        when(productQueryPort.findById(2001L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> handler.handle(command));

        verify(productInventoryPort, never()).reserve(anyLong(), anyInt());
        verify(orderRepositoryPort, never()).save(any(Order.class));
        verify(outboxPort, never()).savePending(any(), anyLong(), any());
    }

    @Test
    void createOrder_priceMismatch_releasesReservedInventory() {
        CreateOrderCommand command = createCommand(
                new CreateOrderCommand.Item(2001L, "상품A", new BigDecimal("35000"), 1),
                new CreateOrderCommand.Item(2002L, "상품B", new BigDecimal("6000"), 1)
        );

        ProductSnapshot firstSnapshot = new ProductSnapshot(2001L, "상품A", new BigDecimal("35000"), 10);
        ProductSnapshot mismatchedSnapshot = new ProductSnapshot(2002L, "상품B", new BigDecimal("5000"), 10);

        when(productQueryPort.findById(2001L)).thenReturn(Optional.of(firstSnapshot));
        when(productQueryPort.findById(2002L)).thenReturn(Optional.of(mismatchedSnapshot));

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(ConflictException.class);

        verify(productInventoryPort).reserve(2001L, 1);
        verify(productInventoryPort, atLeastOnce()).release(2001L, 1);
        verify(orderRepositoryPort, never()).save(any(Order.class));
        verify(outboxPort, never()).savePending(any(), anyLong(), any());
    }

    private CreateOrderCommand createCommand(CreateOrderCommand.Item... items) {
        return new CreateOrderCommand(
                1L,
                "홍길동",
                "010-0000-0000",
                "12345",
                "서울시",
                "강남구",
                List.of(items)
        );
    }
}
