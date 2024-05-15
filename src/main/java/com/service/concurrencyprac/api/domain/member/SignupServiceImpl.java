package com.service.concurrencyprac.api.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignupServiceImpl implements MemberService{

    private final MemberStore memberStore;

    @Override
    public MemberInfo signup(MemberCommand.SignupMemberRequest command) {
        Member initMember = command.toEntity();

        Member member = memberStore.store(initMember);
        return new MemberInfo(member);
    }
}
