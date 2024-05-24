package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.common.util.TokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    private static final String COUPON_PREFIX = "coupon_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String couponType;

    @Column
    private Double amount;

    @OneToMany(mappedBy = "coupon")
    private List<IssuedCoupon> issuedCoupons;
    private String couponToken;
    private String couponName;
    private Double couponDiscountRate;

    private Status status;

    @Builder
    public Coupon(Long userId, String couponName, Double couponDiscountRate) {
        if (userId == null) {
            throw new InvalidParamException("coupon.userId");
        }
        if (StringUtils.isBlank(couponName)) {
            throw new InvalidParamException("coupon.couponName");
        }
        if (couponDiscountRate == null) {
            throw new InvalidParamException("Item.itemPrice");
        }

        this.couponToken = TokenGenerator.randomCharacterWithPrefix(COUPON_PREFIX);
        this.couponName = couponName;
        this.couponDiscountRate = couponDiscountRate;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ACTIVATE("사용 가능"),
        DISABLE("사용 불가");
        private final String description;
    }

    public void changeActivate() {
        this.status = Status.ACTIVATE;
    }

    public void changeDisable() {
        this.status = Status.DISABLE;
    }

    public boolean availableCoupon() {
        return this.status == Status.ACTIVATE;
    }
}
