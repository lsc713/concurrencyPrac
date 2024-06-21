package com.service.concurrencyprac.auth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.MemberCommand.SignupMemberRequest;
import com.service.concurrencyprac.auth.domain.member.MemberInfo;
import com.service.concurrencyprac.auth.repository.member.MemberRepository;
import com.service.concurrencyprac.auth.repository.member.MemberStoreImpl;
import com.service.concurrencyprac.auth.service.member.MemberService;
import com.service.concurrencyprac.common.exception.EntityAlreadyExistException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberStoreImpl memberStore;

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void testSignupMemberSuccessful() {
        SignupMemberRequest request = SignupMemberRequest.builder()
            .email("test@example.com")
            .password("password")
            .memberName("Test Name")
            .nickName("TestNickName")
            .role(Member.UserRole.USER)
            .build();

        MemberInfo memberInfo = memberService.signup(request);

        assertNotNull(memberInfo);
        assertEquals("test@example.com", memberInfo.getEmail());

        Member storedMember = memberRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(storedMember);
        assertEquals("Test Name", storedMember.getName());
        assertEquals("TestNickName", storedMember.getNickName());
        assertEquals("test@example.com", storedMember.getEmail());
        assertEquals(Member.Status.ACTIVATE, storedMember.getStatus());
        assertEquals(Member.UserRole.USER, storedMember.getRole());
    }

    @Test
    public void testSignupMemberAlreadyExists() {
        SignupMemberRequest request = SignupMemberRequest.builder()
            .email("test@example.com")
            .password("password")
            .memberName("Test Name")
            .nickName("TestNickName")
            .role(Member.UserRole.USER)
            .build();

        memberService.signup(request); // First signup

        EntityAlreadyExistException exception = assertThrows(
            EntityAlreadyExistException.class, () -> {
                memberService.signup(request); // Second signup should fail
            });

        assertEquals("test@example.com", exception.getMessage());
    }
}