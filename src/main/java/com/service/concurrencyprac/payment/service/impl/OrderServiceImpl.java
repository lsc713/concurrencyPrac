package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import com.service.concurrencyprac.payment.repository.coupon.OrderRepository;
import com.service.concurrencyprac.payment.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Member member, List<OrderItem> orderItems, int finalAmount,
        ShippingInfo shippingInfo) {
        return null;
    }

    @Override
    public Order completeOrder(Long orderId) {
        return null;
    }
}
