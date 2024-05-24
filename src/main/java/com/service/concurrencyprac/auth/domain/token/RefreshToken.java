package com.service.concurrencyprac.auth.domain.token;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import com.service.concurrencyprac.auth.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String jti;

    @Column
    private String token;

    @Column
    private LocalDateTime expiredAt;

    @Column(columnDefinition = "boolean default false")
    private boolean isRevoke;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;
}
