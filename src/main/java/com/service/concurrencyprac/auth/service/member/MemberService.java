package com.service.concurrencyprac.auth.service.member;


import com.service.concurrencyprac.auth.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.auth.domain.member.MemberInfo;

public interface MemberService {

    MemberInfo signup(SignupMemberRequest command);

}
