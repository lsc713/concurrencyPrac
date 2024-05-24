package com.service.concurrencyprac.payment.facade.impl;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.common.response.ErrorCode;
import com.service.concurrencyprac.payment.dto.CreateOrderDto;
import com.service.concurrencyprac.payment.dto.OrderInfoDto;
import com.service.concurrencyprac.payment.entity.Order;
import com.service.concurrencyprac.payment.entity.Order.Status;
import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.facade.PaymentFacade;
import com.service.concurrencyprac.payment.service.coupon.IssuedCouponService;
import com.service.concurrencyprac.payment.service.order.OrderService;
import com.service.concurrencyprac.payment.service.point.PointService;
import com.service.concurrencyprac.payment.service.product.ProductService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacadeImpl implements PaymentFacade {

    private final OrderService orderService;
    private final PointService pointService;
    private final IssuedCouponService issuedCouponService;
    private final ProductService productService;

    @Override
    @Transactional
    public OrderInfoDto getOrderInfo(Long orderId) {
        Order orderById = orderService.getOrderById(orderId);
        return OrderInfoDto.builder()
            .orderId(orderId)
            .totalPrice(orderById.getAmount())
            .build();
    }

    @Override
    @Transactional
    public Long initOrder(CreateOrderDto createOrderDto) {
        Order order = orderService.createOrder(
            createOrderDto.getUserId(),
            createOrderDto.getOrderItems(),
            createOrderDto.getShippingAddress());
        orderService.applyCouponToOrder(order.getId(), createOrderDto.getCouponId());
        orderService.applyPointToOrder(order.getId(), createOrderDto.getPointAmountToUse());
        return order.getId();
    }

    @Override
    public Long prepareOrder(Long orderId) {
        Map<Product, Integer> productIntegerMap = orderService.generateEntry(orderId);
        productService.decreaseStockQuantity(productIntegerMap);
        orderService.standByOrder(orderId);
        return orderId;
    }

    @Override
    public Long completeOrder(Long orderId, Member member) throws Exception {
        Order orderById = orderService.getOrderById(orderId);
        if (!orderById.getStatus().equals(Status.READY)) {
            throw new InvalidParamException(ErrorCode.ILLEGAL_STATUS_FOR_PAYMENT);
        }
        issuedCouponService.useCoupon(orderById.getUsedIssuedCoupon());
        String reason = "결제건 사용: " + orderById.getOrderNo();

        orderService.completeOrder(orderById.getId());
        return null;
    }

    @Override
    public Long undoOrder(Long orderId) {
        return null;
    }
}
