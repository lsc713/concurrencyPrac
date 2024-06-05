package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    // N+1 문제를 발생시키는 메서드
    List<Order> findAll();

    // N+1 문제를 해결하는 메서드
    @EntityGraph(attributePaths = {"orderItems", "orderItems.product"})
    List<Order> findAllWithOrderItemsBy();
}