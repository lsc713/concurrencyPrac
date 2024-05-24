package com.service.concurrencyprac.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderInfoDto {

    private final Double totalPrice;
    private final Long orderId;

    @Builder
    public OrderInfoDto(Double totalPrice, Long orderId) {
        this.totalPrice = totalPrice;
        this.orderId = orderId;
    }
}
