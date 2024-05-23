package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.entity.Point;
import com.service.concurrencyprac.payment.entity.PointLog;
import com.service.concurrencyprac.payment.entity.PointLog.Type;
import com.service.concurrencyprac.payment.repository.coupon.PointLogRepository;
import com.service.concurrencyprac.payment.repository.coupon.PointRepository;
import com.service.concurrencyprac.payment.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointLogRepository pointLogRepository;

    @Override
    @Transactional
    public void usePoint(Point point, Member member, int amountToUse, String reason, Type type) {
        PointLog log = createAndSavePointLogForUse(point, amountToUse, reason);

        point.addLog(log);
        point.use(amountToUse);
        pointRepository.save(point);
    }

    private PointLog createAndSavePointLogForUse(Point point, int amountToUse, String reason) {
        PointLog log = PointLog.builder()
            .point(point)
            .amount(amountToUse)
            .reason(reason)
            .type(Type.SPEND)
            .build();
        pointLogRepository.save(log);
        return log;
    }

}
