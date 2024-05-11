package com.service.concurrencyprac.api.service;

import com.service.concurrencyprac.api.producer.CouponCreateProducer;
import com.service.concurrencyprac.api.repository.AppliedUserRepository;
import com.service.concurrencyprac.api.repository.CouponCountRepository;
import com.service.concurrencyprac.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository,
        CouponCountRepository couponCountRepository,
        CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {
        Long apply = appliedUserRepository.add(userId);
        if (apply != 1) {
            return;
        }
        Long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }
}
