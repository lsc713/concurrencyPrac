package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import com.service.concurrencyprac.auth.repository.member.MemberRepository;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.repository.coupon.OrderItemRepository;
import com.service.concurrencyprac.payment.repository.coupon.OrderRepository;
import com.service.concurrencyprac.payment.repository.coupon.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class OrderRepositoryPerformanceTest {

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

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAllInBatch();
        orderItemRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void testNPlusOneProblem() {
        // 데이터 준비
        Member member = createMember();
        memberRepository.save(member);

        for (int i = 0; i < 1000; i++) {
            Product product = createProduct("Product" + i, 100.0);
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
        }

        // N+1 문제 발생시키기
        long startTime = System.currentTimeMillis();
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> order.getOrderItems().size());
        long durationNPlusOne = System.currentTimeMillis() - startTime;

        System.out.println("N+1 문제 발생 시간: " + durationNPlusOne + "ms");

        // N+1 문제 해결
        startTime = System.currentTimeMillis();
        List<Order> ordersWithItems = orderRepository.findAllWithOrderItemsBy();
        ordersWithItems.forEach(order -> order.getOrderItems().size());
        long durationFixed = System.currentTimeMillis() - startTime;

        System.out.println("N+1 문제 해결 시간: " + durationFixed + "ms");

        // 성능 개선이 있는지 확인
        assertTrue(durationFixed < durationNPlusOne);
    }
}