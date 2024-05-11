package com.service.concurrencyprac.api.service;

import com.service.concurrencyprac.api.domain.Coupon;
import com.service.concurrencyprac.api.repository.CouponCountRepository;
import com.service.concurrencyprac.api.repository.CouponRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;


    public void apply(Long userId) {
        Long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
