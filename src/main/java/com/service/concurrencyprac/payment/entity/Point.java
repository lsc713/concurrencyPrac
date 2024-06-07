package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Point(Long id, Member member, int availableAmount, List<PointLog> logs) {
        this.id = id;
        this.member = member;
        this.availableAmount = availableAmount;
        this.logs = logs;
    }

    public void use(int amountToUse) {
        if (amountToUse > availableAmount) {
            throw new InvalidParamException();
        }
        this.availableAmount -= amountToUse;
    }

    public void addLog(PointLog log) {
        this.logs.add(log);
    }
}
