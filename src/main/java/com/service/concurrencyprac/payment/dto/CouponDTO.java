package com.service.concurrencyprac.payment.dto;

import lombok.Getter;
import lombok.ToString;

public class CouponDTO {

    @Getter
    @ToString
    public static class ApplyCouponRequest {

        private Long userId;
        private String couponName;
        private Double couponDiscountRate;
    }

}
