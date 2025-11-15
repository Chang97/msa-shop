package com.msashop.order.contexts.order.application.command.handler;

import org.springframework.stereotype.Service;

import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.contexts.order.application.command.port.in.CreateOrderUseCase;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderItem;
import com.msashop.order.contexts.order.domain.model.vo.Address;
import com.msashop.order.contexts.order.domain.model.vo.Money;
import com.msashop.order.contexts.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.contexts.order.domain.port.out.OutboxPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrderHandler implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepo; 
    private final OutboxPort outbox;

    @Transactional
    public long handle(CreateOrderCommand cmd){
        Order o = new Order(
                cmd.userId(), 
                cmd.receiverName(), 
                cmd.receiverPhone(), 
                new Address(cmd.postcode(),cmd.address1(),cmd.address2())
        );
        cmd.items().forEach(i -> o.addItem(new OrderItem(i.productId(), i.productName(), new Money(i.unitPrice()), i.qty())));
        var saved = orderRepo.save(o);
        outbox.savePending("ORDER_CREATED", saved.getId(), "{\"orderId\":"+saved.getId()+"}");
        return saved.getId();
    }
}
