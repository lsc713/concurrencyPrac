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
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.text.DateFormatter;
import lombok.Getter;

@Entity
@Getter
@Table(name = "ORDER_ENTRY")
public class Order {
    private static final String INT_DEFINITION = "int default 0";
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
    private BigDecimal amount;

    @Column(length = 100)
    private String status;

    @OneToMany(mappedBy = "order")
    @Column
    private List<OrderItem> items;

    @Column(columnDefinition = INT_DEFINITION)
    private int pointAmountUsed;

    @OneToOne(mappedBy = "usedOrder")
    private IssuedCoupon usedIssuedCoupon;

    @OneToOne(mappedBy = "order")
    private ShippingInfo shoppingInfo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date refundedAt;

    @Column(length = 1000)
    private String refundReason;

    @Column(precision = 10, scale = 2)
    private BigDecimal refundedAmount;
    @Column
    private Object PGMetadata;

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

}
