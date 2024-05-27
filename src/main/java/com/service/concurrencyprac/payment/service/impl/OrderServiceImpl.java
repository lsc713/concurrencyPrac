package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import com.service.concurrencyprac.payment.repository.coupon.OrderItemRepository;
import com.service.concurrencyprac.payment.repository.coupon.OrderRepository;
import com.service.concurrencyprac.payment.service.order.OrderService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(Member member, List<OrderItem> orderItems, ShippingInfo shippingInfo) {
        Order order = Order.builder()
            .member(member)
            .orderItems(orderItems)
            .shippingInfo(shippingInfo)
            .build();
        return orderRepository.save(order);
    }

    @Override
    public Order addOrderItem(Order order, Product product, int quantity) {
        OrderItem saveOrderItem = createAndSaveOrderItem(order, product, quantity);
        order.getOrderItems().add(saveOrderItem);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Map<Product, Integer> generateEntry(Long orderId) {
        Order orderById = getOrderById(orderId);
        HashMap<Product, Integer> result = new HashMap<>();
        orderById.getOrderItems().forEach(item -> {
            result.put(item.getProduct(), item.getQuantity());
        });
        return result;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository
            .findById(orderId)
            .orElseThrow(InvalidParamException::new);
    }

    @Override
    public Double getOrderCheckoutPrice(Long orderId) {
        return getOrderById(orderId).getCheckoutPrice();
    }

    @Override
    public void applyPointToOrder(Long orderId, Double point) {
        Order orderById = getOrderById(orderId);
        orderById.applyPointToOrder(point);
    }

    @Override
    public void applyCouponToOrder(Long orderId, IssuedCoupon issuedCoupon) {
        Order orderById = getOrderById(orderId);
        orderById.applyCouponToOrder(issuedCoupon);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order orderById = getOrderById(orderId);
        orderById.changeStatus_CANCEL();
        orderRepository.save(orderById);
    }

    @Override
    public void standByOrder(Long orderId) {
        Order orderById = getOrderById(orderId);
        orderById.changeStatus_READY();
    }

    @Override
    public void completeOrder(Long orderId) {
        Order orderById = getOrderById(orderId);
        orderById.changeStatus_COMPLETE();
    }

    private OrderItem createAndSaveOrderItem(Order order, Product product, int quantity) {
        OrderItem orderItem = OrderItem.builder()
            .order(order)
            .product(product)
            .quantity(quantity)
            .build();
        return orderItemRepository.save(orderItem);
    }
}
