package com.service.concurrencyprac.auth.repository.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberReaderImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberReaderImpl memberReader;

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void testGetMemberByIdSuccessful() {
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        Member foundMember = memberReader.getMember(savedMember.getId());

        assertNotNull(foundMember);
        assertEquals(savedMember.getId(), foundMember.getId());
        assertEquals(savedMember.getEmail(), foundMember.getEmail());
    }

    @Test
    public void testGetMemberByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            memberReader.getMember(999L); // Assuming 999L does not exist
        });
    }

    @Test
    public void testGetMemberByEmailSuccessful() {
        Member member = createMember();
        memberRepository.save(member);

        Member foundMember = memberReader.getMember("test@example.com");

        assertNotNull(foundMember);
        assertEquals("test@example.com", foundMember.getEmail());
    }

    @Test
    public void testGetMemberByEmailNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            memberReader.getMember("nonexistent@example.com");
        });
    }

    private static Member createMember() {
        return Member.builder()
            .email("test@example.com")
            .password("password")
            .name("Test Name")
            .nickName("TestNickName")
            .role(Member.UserRole.USER)
            .build();
    }
}