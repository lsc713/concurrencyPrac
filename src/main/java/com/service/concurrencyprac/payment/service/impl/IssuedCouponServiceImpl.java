package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import com.service.concurrencyprac.payment.repository.coupon.IssuedCouponRepository;
import com.service.concurrencyprac.payment.service.coupon.IssuedCouponService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssuedCouponServiceImpl implements IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;

    @Override
    public void useCoupon(IssuedCoupon issuedCoupon) throws Exception {
        issuedCoupon.use();
        issuedCouponRepository.save(issuedCoupon);
    }

    private Optional<IssuedCoupon> findCouponById(Long couponId) {
        return issuedCouponRepository.findById(couponId);
    }
}
