package com.service.concurrencyprac.api.repository;

import com.service.concurrencyprac.api.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
