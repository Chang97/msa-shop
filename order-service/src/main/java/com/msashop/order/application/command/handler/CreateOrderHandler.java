package com.msashop.order.application.command.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.msashop.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.application.command.dto.CreateOrderCommand.Item;
import com.msashop.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.application.command.dto.ReservedItem;
import com.msashop.order.application.command.port.in.CreateOrderUseCase;
import com.msashop.order.domain.model.Order;
import com.msashop.order.domain.model.OrderItem;
import com.msashop.order.domain.model.exception.ProductPriceMismatchException;
import com.msashop.order.domain.model.exception.ProductUnavailableException;
import com.msashop.order.domain.model.vo.Address;
import com.msashop.order.domain.model.vo.Money;
import com.msashop.order.domain.model.vo.ProductSnapshot;
import com.msashop.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.domain.port.out.OutboxPort;
import com.msashop.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.domain.port.out.ProductQueryPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrderHandler implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepo;
    private final OutboxPort outbox;
    private final ProductQueryPort productQueryPort;
    private final ProductInventoryPort productInventoryPort;

    @Transactional
    @Override
    public long handle(CreateOrderCommand cmd) {
        Order order = new Order(
                cmd.userId(),
                cmd.receiverName(),
                cmd.receiverPhone(),
                new Address(cmd.postcode(), cmd.address1(), cmd.address2())
        );

        List<ReservedItem> reservedItems = new ArrayList<>();

        try {
            for (Item item : cmd.items()) {
                ProductSnapshot productSnapshot = productQueryPort.findById(item.productId())
                        .orElseThrow(() -> new ProductUnavailableException(item.productId()));

                if (productSnapshot.stock() < item.qty()) {
                    throw new ProductUnavailableException(
                            "Product " + item.productId() + " has stock=" + productSnapshot.stock());
                }
                if (productSnapshot.price().compareTo(item.unitPrice()) != 0) {
                    throw new ProductPriceMismatchException(item.productId());
                }

                productInventoryPort.reserve(productSnapshot.productId(), item.qty());
                reservedItems.add(new ReservedItem(productSnapshot.productId(), item.qty()));

                order.addItem(new OrderItem(
                        productSnapshot.productId(),
                        productSnapshot.name(),
                        new Money(productSnapshot.price()),
                        item.qty()
                ));
            }
            order.markInventoryReserved();
        } catch (RuntimeException ex) {
            releaseReservedInventory(reservedItems);
            throw ex;
        }

        var saved = orderRepo.save(order);

        List<OrderCreatedEventPayload.OrderItemPayload> itemPayloads = saved.getItems().stream()
                .map(orderItem -> new OrderCreatedEventPayload.OrderItemPayload(
                        orderItem.productId(),
                        orderItem.productName(),
                        orderItem.unitPrice().value(),
                        orderItem.qty(),
                        orderItem.lineAmount().value()
                ))
                .toList();

        OrderCreatedEventPayload payload = new OrderCreatedEventPayload(
                saved.getId(),
                saved.getUserId(),
                saved.getTotal().value(),
                "KRW",
                itemPayloads
        );
        outbox.savePending("ORDER_CREATED", saved.getId(), payload);
        return saved.getId();
    }

    private void releaseReservedInventory(List<ReservedItem> reservedItems) {
        for (int i = reservedItems.size() - 1; i >= 0; i--) {
            var reserved = reservedItems.get(i);
            try {
                productInventoryPort.release(reserved.productId(), reserved.qty());
            } catch (Exception releaseEx) {
                // swallow release failure to avoid masking original exception
            }
        }
    }
}
