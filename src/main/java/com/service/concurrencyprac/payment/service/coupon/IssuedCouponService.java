package com.service.concurrencyprac.payment.service.coupon;

import com.service.concurrencyprac.payment.entity.IssuedCoupon;

public interface IssuedCouponService {

    public void useCoupon(IssuedCoupon issuedCoupon) throws Exception;

}
