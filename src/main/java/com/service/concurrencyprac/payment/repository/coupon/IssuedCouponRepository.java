package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon,Long> {

}
