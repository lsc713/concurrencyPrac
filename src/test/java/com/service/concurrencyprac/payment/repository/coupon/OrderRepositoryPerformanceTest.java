//package com.service.concurrencyprac.payment.repository.coupon;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.service.concurrencyprac.auth.domain.member.Member;
//import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
//import com.service.concurrencyprac.auth.repository.member.MemberRepository;
//import com.service.concurrencyprac.payment.entity.Order;
//import com.service.concurrencyprac.payment.entity.OrderItem;
//import com.service.concurrencyprac.payment.entity.Product;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class OrderRepositoryPerformanceTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryPerformanceTest.class);
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private Member createMember() {
//        return Member.builder()
//            .email("test@example.com")
//            .password("password")
//            .name("Test Name")
//            .nickName("TestNickName")
//            .role(UserRole.USER)
//            .build();
//    }
//
//    private Product createProduct(String name, double price) {
//        return Product.builder()
//            .name(name)
//            .price(price)
//            .stock(10)
//            .build();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        orderItemRepository.deleteAllInBatch();
//        orderRepository.deleteAllInBatch();
//        productRepository.deleteAllInBatch();
//        memberRepository.deleteAllInBatch();
//    }
//
//    @Test
//    public void testNPlusOneProblem() {
//        // 데이터 준비
//        Member member = createMember();
//        memberRepository.save(member);
//
//        for (int i = 0; i < 100; i++) {
//            Product product = createProduct("Product" + i, 100.0);
//            productRepository.save(product);
//
//            OrderItem orderItem = OrderItem.builder()
//                .product(product)
//                .quantity(1)
//                .build();
//            orderItemRepository.save(orderItem);
//
//            Order order = Order.builder()
//                .member(member)
//                .orderItems(new ArrayList<>())
//                .build();
//            order.addOrderItem(orderItem);
//            orderRepository.save(order);
//        }
//
//        // Lazy Loading 방식 시간 측정
//        long totalDurationLazyLoading = 0;
//            long startTime = System.currentTimeMillis();
//            List<Order> orders = orderRepository.findAll();
//            orders.forEach(order -> order.getOrderItems().forEach(OrderItem::getProduct));
//            totalDurationLazyLoading += (System.currentTimeMillis() - startTime);
//        long durationLazyLoading = totalDurationLazyLoading;
//        logger.info("Lazy Loading 방식 평균 시간: {}ms", durationLazyLoading);
//
//        // @EntityGraph 방식 시간 측정
//        long totalDurationEntityGraph = 0;
//            long startEGTime = System.currentTimeMillis();
//            List<Order> ordersWithItems = orderRepository.findAllWithOrderItemsBy();
//            ordersWithItems.forEach(order -> order.getOrderItems().forEach(OrderItem::getProduct));
//            totalDurationEntityGraph += (System.currentTimeMillis() - startEGTime);
//        long durationEntityGraph = totalDurationEntityGraph;
//        logger.info("@EntityGraph 방식 평균 시간: {}ms", durationEntityGraph);
//
//        // 성능 개선이 있는지 확인
//        assertTrue(durationEntityGraph < durationLazyLoading);
//    }
//}