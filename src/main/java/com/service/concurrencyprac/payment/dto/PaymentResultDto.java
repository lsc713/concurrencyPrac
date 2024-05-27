package com.service.concurrencyprac.payment.dto;

import lombok.Getter;

@Getter
public class PaymentResultDto {

    private boolean isPaymentSuccess;
    private String message;

    public boolean paymentSuccess() {
        return isPaymentSuccess = true;
    }

    public boolean paymentFalse() {
        return isPaymentSuccess = false;
    }

    public String enrollMessage(String message) {
        return this.message = message;
    }
}
