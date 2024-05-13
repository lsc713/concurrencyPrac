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

    /*TODO 이미지 지원한 것에 대한 추가 안내가 필요하고, 100개가 넘어가면 추가적인 안내가 필요함.
     *  또한 지원이 됐다면 지원됐다는 안내도 필요.*/
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
