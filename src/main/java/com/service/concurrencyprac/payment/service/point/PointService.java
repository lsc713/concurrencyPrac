package com.service.concurrencyprac.payment.service.point;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.entity.Point;
import com.service.concurrencyprac.payment.entity.PointLog.Type;

public interface PointService {

    public void usePoint(Point point, int amountToUse, String reason, Type type);

}
