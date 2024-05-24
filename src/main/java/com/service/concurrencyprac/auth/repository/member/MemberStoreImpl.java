package com.service.concurrencyprac.auth.repository.member;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.MemberStore;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

    private final MemberRepository memberRepository;

    @Override
    public Member store(Member signupMember) {
        if (StringUtils.isEmpty(signupMember.getUserToken())) {
            throw new InvalidParamException("signupMember.getPartnerToken()");
        }
        if (StringUtils.isEmpty(signupMember.getEmail())) {
            throw new InvalidParamException("signupMember.getPartnerToken()");
        }
        if (StringUtils.isEmpty(signupMember.getName())) {
            throw new InvalidParamException("signupMember.getPartnerToken()");
        }
        if (StringUtils.isEmpty(signupMember.getNickName())) {
            throw new InvalidParamException("signupMember.getPartnerToken()");
        }
        if (signupMember.getStatus() == null) {
            throw new InvalidParamException("signupMember.getStatus()");
        }

        memberRepository.findByEmail(signupMember.getEmail())
            .orElseThrow(() -> new EntityNotFoundException("signupMember.getEmail()"));

        return memberRepository.save(signupMember);
    }
}
