package com.service.concurrencyprac.api.repository.member;

import com.service.concurrencyprac.api.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
