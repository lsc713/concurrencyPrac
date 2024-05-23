package com.service.concurrencyprac.payment.service.point;

import com.service.concurrencyprac.api.domain.member.Member;

public interface PointService {

    public void usePoint(Member member, int amountToUse, String reason);

}
