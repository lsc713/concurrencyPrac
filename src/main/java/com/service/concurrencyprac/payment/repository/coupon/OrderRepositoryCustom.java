package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.Order;
import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findAllWithOrderItems();

}
