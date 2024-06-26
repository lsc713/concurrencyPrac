package com.service.concurrencyprac.auth.domain.member;

import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class SignupMemberRequest {

        private final String email;
        private final String password;
        private final String memberName;
        private final String nickName;
        private final UserRole role;

        public Member toEntity(String password) {
            return Member.builder()
                .email(email)
                .password(password)
                .name(memberName)
                .nickName(nickName)
                .role(role)
                .build();
        }
    }
}
