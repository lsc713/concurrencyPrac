package com.service.concurrencyprac.api.controller;

import com.service.concurrencyprac.api.domain.coupon.ApplyService;
import com.service.concurrencyprac.api.dto.CouponDTO;
import com.service.concurrencyprac.common.response.CommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/coupon")
public class CouponApiController {

    private final ApplyService applyService;

    public CouponApiController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping
    public CommonResponse ApplyCoupon(@RequestBody CouponDTO.ApplyCouponRequest request) {
        Long userId = request.getUserId();

        applyService.apply(userId);
        return CommonResponse.success("OK");
    }

}