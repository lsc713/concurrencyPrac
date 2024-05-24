package com.service.concurrencyprac.auth.service.impl;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.auth.domain.member.MemberInfo;
import com.service.concurrencyprac.auth.domain.member.MemberStore;
import com.service.concurrencyprac.auth.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberStore memberStore;

    @Override
    public MemberInfo signup(SignupMemberRequest command) {
        String encoded = passwordEncoder.encode(command.getPassword());
        Member initMember = command.toEntity(encoded);

        Member member = memberStore.store(initMember);
        return new MemberInfo(member);
    }
}
