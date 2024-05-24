package com.service.concurrencyprac.payment.dto;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.payment.entity.IssuedCoupon;
import com.service.concurrencyprac.payment.entity.OrderItem;
import com.service.concurrencyprac.payment.entity.ShippingInfo;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateOrderDto {

    private Member userId;
    private List<OrderItem> orderItems;
    private IssuedCoupon couponId;
    private Double pointAmountToUse;
    private ShippingInfo shippingAddress;

}
