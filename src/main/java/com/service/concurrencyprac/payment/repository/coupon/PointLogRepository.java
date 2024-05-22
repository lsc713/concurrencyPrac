package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogRepository extends JpaRepository<PointLog,Long> {

}
