package com.service.concurrencyprac.payment.dto;

import com.service.concurrencyprac.payment.entity.OrderItem;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateOrderDto {

    private String userId;
    private List<OrderItem> orderItems;
    private String couponId;
    private int pointAmountToUse;
    private String shippingAddress;

}
