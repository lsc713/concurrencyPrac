package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import com.service.concurrencyprac.auth.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;

@Entity
@Getter
public class IssuedCoupon extends BaseEntity {

    private static final String BOOLEAN_DEFINITION = "boolean default false";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToOne
    private Order usedOrder;

    @Column(columnDefinition = BOOLEAN_DEFINITION)
    private boolean isValid;

    @Column(columnDefinition = BOOLEAN_DEFINITION)
    private boolean isUsed;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date usedAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    public void use() {
        this.isUsed=true;
        this.isValid=false;
        this.usedAt=new Date();
    }
}
