package com.service.concurrencyprac.api.repository.coupon;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository {

    private static final String COUPON_COUNT = "coupon_count";

    private final RedisTemplate<String, String> redisTemplate;

    public CouponCountRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment() {
        return redisTemplate
            .opsForValue()
            .increment(COUPON_COUNT);
    }
}
