package com.service.concurrencyprac.api.dto;

import lombok.Getter;
import lombok.ToString;

public class CouponDto {

    @Getter
    @ToString
    public static class ApplyCouponRequest {

        private Long userId;
        private String couponName;
        private Double couponDiscountRate;
    }

}
