package com.service.concurrencyprac.auth.domain.member;

import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfo {

    private final Long id;
    private final String memberToken;
    private final String memberName;
    private final String email;
    private final Member.Status status;
    private final UserRole role;

    public MemberInfo(Member member) {
        this.id = member.getId();
        this.memberToken = member.getMemberToken();
        this.memberName = member.getName();
        this.email = member.getEmail();
        this.status = member.getStatus();
        this.role = member.getRole();
    }

}
