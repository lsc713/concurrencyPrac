package com.service.concurrencyprac.api.domain.member;

import lombok.Getter;

@Getter
public class MemberInfo {

    private final Long id;
    private final String memberToken;
    private final String memberName;
    private final String email;
    private final Member.Status status;
    private final Member.Role role;

    public MemberInfo(Member member) {
        this.id = member.getId();
        this.memberToken = member.getUserToken();
        this.memberName = member.getName();
        this.email = member.getEmail();
        this.status = member.getStatus();
        this.role = member.getRole();
    }

}