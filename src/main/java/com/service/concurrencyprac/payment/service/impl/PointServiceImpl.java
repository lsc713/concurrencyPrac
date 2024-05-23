package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.repository.coupon.PointRepository;
import com.service.concurrencyprac.payment.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public void usePoint(Member member, int amountToUse, String reason) {

    }
}
