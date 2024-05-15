package com.service.concurrencyprac.api.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class SignupMemberRequest {
        private final String email;
        private final String memberName;
        private final String nickName;

        public Member toEntity() {
            return Member.builder()
                .email(email)
                .name(memberName)
                .nickName(nickName)
                .build();
        }

    }

}
