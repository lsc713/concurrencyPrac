package com.service.concurrencyprac.payment.service.order;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Order createOrder(Member member, List<OrderItem> orderItems, double finalAmount,
        ShippingInfo shippingInfo);

    public Order addOrderItem(Order order, Product product, int quantity);

    public Map<Product, Integer> generateEntry(Long orderId);

    Order getOrderById(Long orderId);

    /*최종 결제 가격 확인*/
    Double getOrderCheckoutPrice(Long orderId);

    void applyPointToOrder(Long orderId, Double point);

    void applyCouponToOrder(Long orderId, IssuedCoupon issuedCoupon);

    void standByOrder(Long orderId);
    /*
    * Order 완료에 대한 상태 저장
    * */
    void completeOrder(Long orderId);

}
