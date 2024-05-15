package com.service.concurrencyprac.api.domain.member;


public interface MemberService {

    MemberInfo signup(MemberCommand.SignupMemberRequest command);

}
