package com.service.concurrencyprac.auth.repository.member;

import com.service.concurrencyprac.auth.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberToken(String memberToken);
}
