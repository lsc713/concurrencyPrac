package com.service.concurrencyprac.api.service.member;


import com.service.concurrencyprac.api.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.api.domain.member.MemberInfo;

public interface MemberService {

    MemberInfo signup(SignupMemberRequest command);

}
