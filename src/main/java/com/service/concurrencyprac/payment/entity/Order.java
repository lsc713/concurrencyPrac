package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.api.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "ORDER_ENTRY")
public class Order {
    private static final String DOUBLE_DEFINITION = "double default 0";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String REGEX_ORDER = "[^a-zA-Z0-9]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(length = 255)
    private String orderNo;

    @Column
    private Double amount;

    @Column(length = 100)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status{
        READY("준비됨"), COMPLETE("완료됨")
        ;
        private final String description;
    }

    @OneToMany(mappedBy = "order")
    @Column
    private List<OrderItem> items;

    @Column(columnDefinition = DOUBLE_DEFINITION)
    private Double pointAmountUsed;

    @OneToOne(mappedBy = "usedOrder")
    private IssuedCoupon usedIssuedCoupon;

    @OneToOne(mappedBy = "order")
    private ShippingInfo shippingInfo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date refundedAt;

    @Column(length = 1000)
    private String refundReason;

    @Column(precision = 10, scale = 2)
    private BigDecimal refundedAmount;

    @Column
    private Object PGMetadata;

    public Order(Member member, List<OrderItem> items, ShippingInfo shippingInfo) {
        this.member = member;
        this.amount = getCheckoutPrice();
        this.items = items;
        this.shippingInfo = shippingInfo;
        setOrderNo();
    }

    private double getCheckoutPrice() {
        double amount = items.stream().mapToDouble(OrderItem::getEntryPrice).sum();
        amount -= this.pointAmountUsed;

        Coupon coupon = this.usedIssuedCoupon.getCoupon();
        if (coupon != null) {
            if (coupon.getCouponType().equalsIgnoreCase("PERCENT-OFF")) {
                amount *= (1 - coupon.getAmount());
            } else if (coupon.getCouponType().equalsIgnoreCase("FIXED-AMOUNT-OFF")) {
                amount -= coupon.getAmount();
            }
        }
        this.amount = amount;
        return amount;
    }

    public Order() {
        super();
        setOrderNo();
    }

    private void setOrderNo() {
        String dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT)
            .format(LocalDateTime.now());
        String randomString = UUID.randomUUID().toString().replace(REGEX_ORDER,"").substring(0,15);
        this.orderNo = dateFormat + "_" + randomString;
    }

    public void applyCouponToOrder(IssuedCoupon issuedCoupon) {
        this.usedIssuedCoupon = issuedCoupon;
        this.amount = getCheckoutPrice();
    }

    public void applyPointToOrder(Double pointAmountUsed) {
        this.pointAmountUsed = pointAmountUsed;
        this.amount = getCheckoutPrice();
    }

}
