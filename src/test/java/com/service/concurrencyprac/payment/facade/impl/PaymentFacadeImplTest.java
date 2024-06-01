package com.service.concurrencyprac.payment.facade.impl;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import com.service.concurrencyprac.auth.repository.member.MemberRepository;
import com.service.concurrencyprac.payment.dto.CreateOrderDto;
import com.service.concurrencyprac.payment.dto.OrderInfoDto;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import com.service.concurrencyprac.payment.repository.coupon.OrderItemRepository;
import com.service.concurrencyprac.payment.repository.coupon.OrderRepository;
import com.service.concurrencyprac.payment.repository.coupon.ProductRepository;
import com.service.concurrencyprac.payment.service.coupon.IssuedCouponService;
import com.service.concurrencyprac.payment.service.order.OrderService;
import com.service.concurrencyprac.payment.service.point.PointService;
import com.service.concurrencyprac.payment.service.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PaymentFacadeImplTest {

    @Autowired
    private PaymentFacadeImpl paymentFacade;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PointService pointService;

    @Autowired
    private IssuedCouponService issuedCouponService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member createMember() {
        return Member.builder()
            .email("test@example.com")
            .password("password")
            .name("Test Name")
            .nickName("TestNickName")
            .role(UserRole.USER)
            .build();
    }

    private Product createProduct(String name, double price) {
        return Product.builder()
            .name(name)
            .price(price)
            .stock(10)
            .build();
    }

    private IssuedCoupon createIssuedCoupon() {
        // Create and return an IssuedCoupon object as needed
        return null;
    }

    private ShippingInfo createShippingInfo() {
        // Create and return a ShippingInfo object as needed
        return null;
    }

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAllInBatch();
        orderItemRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
    @Test
    public void testGetOrderInfo() {
        Member member = createMember();
        memberRepository.save(member);
        Product product = createProduct("Product", 100.0);
        productRepository.save(product);

        OrderItem orderItem = OrderItem.builder()
            .product(product)
            .quantity(1)
            .build();
        orderItemRepository.save(orderItem);

        Order order = Order.builder()
            .member(member)
            .orderItems(new ArrayList<>())
            .build();
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);

        OrderInfoDto orderInfo = paymentFacade.getOrderInfo(order.getId());
        assertNotNull(orderInfo);
        assertEquals(order.getId(), orderInfo.getOrderId());
        assertEquals(order.getAmount(), orderInfo.getTotalPrice());
    }

    @Test
    public void testInitOrder() {
        Member member = createMember();
        memberRepository.save(member);
        Product product = createProduct("Product", 100.0);
        productRepository.save(product);

        OrderItem orderItem = OrderItem.builder()
            .product(product)
            .quantity(2)
            .build();

        IssuedCoupon issuedCoupon = createIssuedCoupon();
        ShippingInfo shippingInfo = createShippingInfo();

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
            .userId(member)
            .orderItems(List.of(orderItem))
            .couponId(issuedCoupon)
            .pointAmountToUse(10.0)
            .shippingAddress(shippingInfo)
            .build();

        Long orderId = paymentFacade.initOrder(createOrderDto);
        assertNotNull(orderId);

        Order order = orderRepository.findById(orderId).orElse(null);
        assertNotNull(order);
        assertEquals(createOrderDto.getUserId().getId(), order.getMember().getId());
    }

    @Test
    public void testPrepareOrder() {
        Member member = createMember();
        memberRepository.save(member);
        Product product = createProduct("Product", 100.0);
        productRepository.save(product);

        OrderItem orderItem = OrderItem.builder()
            .product(product)
            .quantity(2)
            .build();
        orderItemRepository.save(orderItem);

        Order order = Order.builder()
            .member(member)
            .orderItems(new ArrayList<>())
            .build();
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);

        Long orderId = paymentFacade.prepareOrder(order.getId());
        assertNotNull(orderId);

        Order updatedOrder = orderRepository.findById(orderId).orElse(null);
        assertNotNull(updatedOrder);
        assertEquals(Order.Status.READY, updatedOrder.getStatus());
    }

    @Test
    public void testCompleteOrder() throws Exception {
        Member member = createMember();
        memberRepository.save(member);
        Product product = createProduct("Product", 100.0);
        productRepository.save(product);

        OrderItem orderItem = OrderItem.builder()
            .product(product)
            .quantity(2)
            .build();
        orderItemRepository.save(orderItem);

        Order order = Order.builder()
            .member(member)
            .orderItems(new ArrayList<>())
            .build();
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);

        order.changeStatus_READY();
        orderRepository.save(order);

        Long orderId = paymentFacade.completeOrder(order.getId(), member);
        assertNotNull(orderId);

        Order updatedOrder = orderRepository.findById(orderId).orElse(null);
        assertNotNull(updatedOrder);
        assertEquals(Order.Status.COMPLETE, updatedOrder.getStatus());
    }
}