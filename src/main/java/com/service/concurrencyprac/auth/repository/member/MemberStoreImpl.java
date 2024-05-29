package com.service.concurrencyprac.auth.repository.member;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.Status;
import com.service.concurrencyprac.auth.domain.member.MemberStore;
import com.service.concurrencyprac.common.exception.EntityAlreadyExistException;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.common.response.ErrorCode;
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
        if (StringUtils.isEmpty(signupMember.getMemberToken())) {
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
        if (signupMember.getStatus() == Status.DISABLED) {
            throw new InvalidParamException("signupMember.getStatus()");
        }

        if (memberRepository.findByEmail(signupMember.getEmail()).isPresent()) {
            throw new EntityAlreadyExistException(signupMember.getEmail());
        }
        return memberRepository.save(signupMember);
    }
}
