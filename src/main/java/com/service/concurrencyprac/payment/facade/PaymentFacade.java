package com.service.concurrencyprac.payment.facade;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.payment.dto.CreateOrderDto;
import com.service.concurrencyprac.payment.dto.OrderInfoDto;

public interface PaymentFacade {

    OrderInfoDto getOrderInfo(Long orderId);

    Long initOrder(CreateOrderDto createOrderDto);

    Long prepareOrder(Long orderId);

    Long completeOrder(Long orderId, Member member) throws Exception;

    Long undoOrder(Long orderId);

}
