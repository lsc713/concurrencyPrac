package com.service.concurrencyprac.api.repository.member;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.api.domain.member.MemberStore;
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
        if (StringUtils.isEmpty(signupMember.getUserToken())) throw new InvalidParamException("partner.getPartnerToken()");
        if (StringUtils.isEmpty(signupMember.getEmail())) throw new InvalidParamException("partner.getPartnerToken()");
        if (StringUtils.isEmpty(signupMember.getName())) throw new InvalidParamException("partner.getPartnerToken()");
        if (StringUtils.isEmpty(signupMember.getNickName())) throw new InvalidParamException("partner.getPartnerToken()");
        if (signupMember.getStatus() == null) throw new InvalidParamException("partner.getStatus()");

        return memberRepository.save(signupMember);
    }
}
