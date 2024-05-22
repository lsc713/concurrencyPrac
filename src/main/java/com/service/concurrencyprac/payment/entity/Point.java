package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.api.domain.BaseEntity;
import com.service.concurrencyprac.api.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column
    private int availableAmount;

    @OneToMany(mappedBy = "point")
    private List<PointLog> logs;

    public void use(int amountToUse) {
        this.availableAmount -= amountToUse;
    }
}
