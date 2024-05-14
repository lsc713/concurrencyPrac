package com.service.concurrencyprac.api.controller;

import com.service.concurrencyprac.common.response.CommonResponse;
import com.service.concurrencyprac.api.dto.CouponDto;
import com.service.concurrencyprac.api.service.ApplyService;
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
    public CommonResponse ApplyCoupon(@RequestBody CouponDto.ApplyCouponRequest request) {
        Long userId = request.getUserId();

        applyService.apply(userId);
        return CommonResponse.success("OK");
    }

}
