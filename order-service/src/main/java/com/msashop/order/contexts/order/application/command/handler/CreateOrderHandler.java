package com.msashop.order.contexts.order.application.command.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand.Item;
import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.contexts.order.application.command.dto.ReservedItem;
import com.msashop.order.contexts.order.application.command.port.in.CreateOrderUseCase;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderItem;
import com.msashop.order.contexts.order.domain.model.vo.Address;
import com.msashop.order.contexts.order.domain.model.vo.Money;
import com.msashop.order.contexts.order.domain.model.vo.ProductSnapshot;
import com.msashop.order.contexts.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.contexts.order.domain.port.out.OutboxPort;
import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.contexts.order.domain.port.out.ProductQueryPort;
import com.msashop.order.platform.exception.ConflictException;
import com.msashop.order.platform.exception.NotFoundException;

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
    public long handle(CreateOrderCommand cmd){
        Order o = new Order(
                cmd.userId(), 
                cmd.receiverName(), 
                cmd.receiverPhone(), 
                new Address(cmd.postcode(),cmd.address1(),cmd.address2())
        );

        List<ProductSnapshot> snapshots = new ArrayList<>();
        List<ReservedItem> reservedItems = new ArrayList<>();

        try {
            for (Item item : cmd.items()) {
                ProductSnapshot productSnapshot = productQueryPort.findById(item.productId()).orElseThrow(() ->
                new NotFoundException("Product not Found"));
                if (productSnapshot.stock() < item.qty()) {
                    throw new ConflictException("product " + item.productId() + " stock=" + productSnapshot.stock());
                }
                if (productSnapshot.price().compareTo(item.unitPrice()) != 0) {
                    throw new ConflictException("product " + item.productId() + " price mismatch");
                }
                snapshots.add(productSnapshot);
                productInventoryPort.reserve(productSnapshot.productId(), item.qty());
                reservedItems.add(new ReservedItem(productSnapshot.productId(), item.qty()));
                
                o.addItem(new OrderItem(productSnapshot.productId(), productSnapshot.name(), new Money(productSnapshot.price()), item.qty()));
            }
            o.markInventoryReserved();
        } catch (RuntimeException ex) {
            // 이미 예약한 항목은 반드시 release
            for (int i = reservedItems.size() - 1; i >= 0; i--) {
                var reserved = reservedItems.get(i);
                try {
                    productInventoryPort.release(reserved.productId(), reserved.qty());
                } catch (Exception releaseEx) {
                    // 로깅만 하고 계속 진행하거나, release 실패도 감싸서 던질지 정책 결정
                }
            }

            throw ex;
        }

        var saved = orderRepo.save(o);

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
}
