package com.service.concurrencyprac.api.repository.coupon;

import com.service.concurrencyprac.api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}