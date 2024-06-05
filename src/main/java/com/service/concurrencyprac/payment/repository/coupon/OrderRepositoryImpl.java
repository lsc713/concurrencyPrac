package com.service.concurrencyprac.payment.repository.coupon;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.QOrder;
import com.service.concurrencyprac.payment.entity.QOrderItem;
import com.service.concurrencyprac.payment.entity.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAllWithOrderItems() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QOrder order = QOrder.order;
        QOrderItem orderItem = QOrderItem.orderItem;
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(order)
            .leftJoin(order.orderItems, orderItem).fetchJoin()
            .leftJoin(orderItem.product, product).fetchJoin()
            .fetch();
    }
}
