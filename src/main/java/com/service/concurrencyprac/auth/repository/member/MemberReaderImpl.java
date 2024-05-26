package com.service.concurrencyprac.auth.repository.member;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(EntityNotFoundException::new);
    }
}
