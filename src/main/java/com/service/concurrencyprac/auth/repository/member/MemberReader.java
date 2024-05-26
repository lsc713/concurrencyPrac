package com.service.concurrencyprac.auth.repository.member;

import com.service.concurrencyprac.auth.domain.member.Member;

public interface MemberReader {

    Member getMember(Long memberId);

    Member getMember(String email);

}
