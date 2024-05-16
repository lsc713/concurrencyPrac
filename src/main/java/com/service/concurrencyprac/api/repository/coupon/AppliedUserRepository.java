package com.service.concurrencyprac.api.repository.coupon;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedUserRepository {

    private static final String APPLIED_USER = "applied_user";

    private final RedisTemplate<String, String> redisTemplate;

    public AppliedUserRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long add(Long userId) {
        return redisTemplate
            .opsForSet()
            .add(APPLIED_USER, userId.toString());
    }
}
