package com.msashop.order.contexts.order.adapter.out.persistence.query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.order.contexts.order.adapter.out.persistence.entity.QOrderEntity;
import com.msashop.order.contexts.order.application.query.dto.OrderQueryCondition;
import com.msashop.order.contexts.order.application.query.dto.OrderSummaryView;
import com.msashop.order.contexts.order.application.query.dto.PageResult;
import com.msashop.order.contexts.order.application.query.port.out.OrderQueryPort;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryAdapter implements OrderQueryPort {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageResult<OrderSummaryView> findOrders(OrderQueryCondition cond) {
        QOrderEntity order = QOrderEntity.orderEntity;

        BooleanBuilder predicate = new BooleanBuilder();
        if (cond.userId() != null) {
            predicate.and(order.userId.eq(cond.userId()));
        }
        if (cond.statuses() != null && !cond.statuses().isEmpty()) {
            predicate.and(order.status.in(
                    cond.statuses().stream().map(Enum::name).toList()
            ));
        }
        if (cond.from() != null) {
            predicate.and(order.createdAt.goe(cond.from()));
        }
        if (cond.to() != null) {
            predicate.and(order.createdAt.loe(cond.to()));
        }

        List<OrderSummaryView> content = queryFactory
                .select(Projections.constructor(OrderSummaryView.class,
                        order.id,
                        order.orderNumber,
                        order.status.stringValue(),
                        order.totalAmount,
                        order.userId,
                        order.createdAt))
                .from(order)
                .where(predicate)
                .orderBy(order.createdAt.desc())
                .offset((long) cond.page() * cond.size())
                .limit(cond.size())
                .fetch();

        Long totalResult = queryFactory
                .select(order.count())
                .from(order)
                .where(predicate)
                .fetchOne();

        long total = totalResult != null ? totalResult : 0L;
        return new PageResult<>(content, cond.page(), cond.size(), total);
    }
}
