package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
