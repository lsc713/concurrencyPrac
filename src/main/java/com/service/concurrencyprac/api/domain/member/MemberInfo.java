package com.service.concurrencyprac.api.domain.member;

import com.service.concurrencyprac.api.domain.member.Member.Status;
import lombok.Getter;

@Getter
public class MemberInfo {

    private final Long id;
    private final String memberToken;
    private final String memberName;
    private final String email;
    private final Member.Status status;

    public MemberInfo(Member member) {
        this.id = member.getId();
        this.memberToken = member.getUserToken();
        this.memberName = member.getName();
        this.email = member.getEmail();
        this.status = member.getStatus();
    }

}
