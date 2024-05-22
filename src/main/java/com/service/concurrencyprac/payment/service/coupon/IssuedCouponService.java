package com.service.concurrencyprac.payment.service.coupon;

public interface IssuedCouponService {

    public void useCoupon(Long couponId) throws Exception;

}
