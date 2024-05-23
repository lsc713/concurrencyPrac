package com.service.concurrencyprac.payment.service.order;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import java.util.List;

public interface OrderService {

    public Order createOrder(Member member, List<OrderItem> orderItems, int finalAmount,
        ShippingInfo shippingInfo);

    /*
    * Order 완료에 대한 상태 저장
    * */
    public Order completeOrder(Long orderId);

}
