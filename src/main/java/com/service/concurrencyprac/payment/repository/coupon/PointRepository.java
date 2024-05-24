package com.service.concurrencyprac.payment.repository.coupon;

import com.service.concurrencyprac.payment.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Long> {

}
