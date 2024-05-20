package com.service.concurrencyprac.api.domain.member;

import com.service.concurrencyprac.common.util.TokenGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private static final String PREFIX_MEMBER = "member_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userToken;
    private String email;
    private String name;
    private String nickName;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ACTIVATE("활성화"), DISABLED("비활성화");

        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Role{
        ADMIN("관리자"),MANAGER("매니저"), USER("일반유저")
        ;
        private final String description;
    }

    @Builder
    public Member(String email, String password, String name, String nickName, Role role) {

        this.userToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_MEMBER);
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.status = Status.ACTIVATE;
        this.role = role;
    }

    public void enable() {
        this.status = Status.ACTIVATE;
    }

    public void disable() {
        this.status = Status.DISABLED;
    }

}