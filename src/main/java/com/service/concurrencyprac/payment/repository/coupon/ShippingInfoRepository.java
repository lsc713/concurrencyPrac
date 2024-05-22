package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo,Long> {

}
