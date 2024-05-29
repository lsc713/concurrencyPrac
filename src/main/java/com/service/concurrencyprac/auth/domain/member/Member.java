package com.service.concurrencyprac.auth.domain.member;

import com.service.concurrencyprac.auth.domain.token.AccessLog;
import com.service.concurrencyprac.auth.domain.token.AccessToken;
import com.service.concurrencyprac.auth.domain.token.RefreshToken;
import com.service.concurrencyprac.common.util.TokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
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

    private String memberToken;
    @Column
    private String email;
    @Column
    private String name;
    private String nickName;
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "member")
    private List<AccessToken> accessTokens;

    @OneToMany(mappedBy = "member")
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "member")
    private List<AccessLog> accessLogs;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ACTIVATE("활성화"), DISABLED("비활성화"),
        ;

        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum UserRole {
        ADMIN(Description.ADMIN), MANAGER(Description.MANAGER), USER(Description.USER);
        private final String description;

        public static class Description {

            public static final String USER = "일반유저";
            public static final String MANAGER = "중간관리자";
            public static final String ADMIN = "관리자";
        }
    }

    @Builder(toBuilder = true)
    public Member(String email, String password, String name, String nickName, UserRole role) {
        this.memberToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_MEMBER);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
