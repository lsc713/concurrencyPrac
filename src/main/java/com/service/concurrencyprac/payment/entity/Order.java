package com.service.concurrencyprac.payment.entity;

import static com.service.concurrencyprac.payment.entity.Order.Status.CANCEL;
import static com.service.concurrencyprac.payment.entity.Order.Status.COMPLETE;
import static com.service.concurrencyprac.payment.entity.Order.Status.READY;

import com.service.concurrencyprac.auth.domain.member.Member;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Table(name = "ORDER_ENTRY")
public class Order {

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String REGEX_ORDER = "[^a-zA-Z0-9]";
    private static final String COUPON_TYPE_PERCENT = "PERCENT-OFF";
    private static final int ORDER_STRING_LENGTH = 15;
    private static final int ALPHABET_LENGTH = 26;

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
    public enum Status {
        READY("준비됨"), COMPLETE("주문완료됨"), CANCEL("주문취소");
        private final String description;
    }

    @OneToMany(mappedBy = "order")
    @Column
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column
    private Double pointAmountUsed = 0.0;

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

//    @Column
//    private Object PGMetadata;

    @Builder
    public Order(Member member, List<OrderItem> orderItems, ShippingInfo shippingInfo) {
        this.member = member;
        this.amount = getCheckoutPrice();
        this.orderItems = orderItems;
        this.shippingInfo = shippingInfo;
        setOrderNo();
    }

    public Order() {
        super();
        setOrderNo();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.orderSetting(this);
    }

    private void setOrderNo() {
        String dateFormat = LocalDateTime
            .now()
            .format(DateTimeFormatter
                .ofPattern(DATE_FORMAT));

        StringBuilder randomString = generateRandomString(ORDER_STRING_LENGTH);

        this.orderNo = dateFormat + "_" + randomString;
    }

    public double getCheckoutPrice() {
        double amount = orderItems.stream().mapToDouble(OrderItem::getEntryPrice).sum();
        amount -= this.pointAmountUsed;

        if (this.usedIssuedCoupon != null) {
            Coupon coupon = this.usedIssuedCoupon.getCoupon();
            if (coupon != null) {
                amount = applyCouponDisCount(coupon, amount);
            }
        }
        this.amount = amount;
        return amount;
    }

    private static double applyCouponDisCount(Coupon coupon, double amount) {
        if (coupon.getCouponType().equalsIgnoreCase(COUPON_TYPE_PERCENT)) {
            return applyPercentOff(coupon, amount);
        }
        return applyFixedAmountOff(coupon, amount);
    }

    private static double applyFixedAmountOff(Coupon coupon, double amount) {
        return amount - coupon.getAmount();
    }

    private static double applyPercentOff(Coupon coupon, double amount) {
        return amount * (1 - coupon.getAmount());
    }

    public void applyCouponToOrder(IssuedCoupon issuedCoupon) {
        this.usedIssuedCoupon = issuedCoupon;
        this.amount = getCheckoutPrice();
    }

    public void applyPointToOrder(Double pointAmountUsed) {
        this.pointAmountUsed = pointAmountUsed;
        this.amount = getCheckoutPrice();
    }

    private static StringBuilder generateRandomString(int length) {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('A' + random.nextInt(ALPHABET_LENGTH));
            randomString.append(randomChar);
        }
        return randomString;
    }

    public void changeStatus_READY() {
        this.status = READY;
    }

    public void changeStatus_COMPLETE() {
        this.status = COMPLETE;
    }

    public void changeStatus_CANCEL() {
        this.status = CANCEL;
    }
}
