package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointLog extends BaseEntity {

    private static final String INT_DEFINITION = "int default 0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @Column(columnDefinition = INT_DEFINITION)
    private int amount;

    @Column(length = 1000)
    private String reason;

    @Column(length = 50)
    private Type type;

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        SPEND("사용"),
        EARN("적립");
        private final String description;
    }

    @Builder
    public PointLog(Point point, int amount, String reason, Type type) {
        this.point = point;
        this.amount = amount;
        this.reason = reason;
        this.type = type;
    }
}
