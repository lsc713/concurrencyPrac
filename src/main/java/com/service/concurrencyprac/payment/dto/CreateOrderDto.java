package com.service.concurrencyprac.payment.dto;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderDto {

    private final Member userId;
    private final List<OrderItem> orderItems;
    private final IssuedCoupon couponId;
    private final Double pointAmountToUse;
    private final ShippingInfo shippingAddress;

}
