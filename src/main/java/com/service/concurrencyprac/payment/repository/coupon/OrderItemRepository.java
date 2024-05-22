package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
