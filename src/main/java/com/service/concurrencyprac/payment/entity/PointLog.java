package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.api.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
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
    public enum Type{
        SPEND("사용"),EARN("적립");
        private final String description;
    }

    public void use(int amount, String reason) {
        this.amount = amount;
        this.reason = reason;
        this.type = Type.SPEND;
    }

    public void add(int amount, String reason) {
        this.amount = amount;
        this.reason = reason;
        this.type = Type.EARN;
    }
}
