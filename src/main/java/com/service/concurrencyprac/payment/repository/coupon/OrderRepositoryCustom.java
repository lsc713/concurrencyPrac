package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

public interface OrderRepositoryCustom {

    List<Order> findAllWithOrderItems();

}
