package com.service.concurrencyprac.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import com.service.concurrencyprac.auth.repository.member.MemberRepository;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;
import com.service.concurrencyprac.security.service.UserDetailsImpl;
import com.service.concurrencyprac.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Member savedMember;

    @BeforeEach
    public void setUp() {
        Member member = Member.builder()
            .email("test@example.com")
            .password(passwordEncoder.encode("password"))
            .name("Test Name")
            .nickName("TestNickName")
            .role(UserRole.USER)
            .build();
        savedMember = memberRepository.save(member);
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    public void testLoadUserByUsername_Success() {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals(savedMember.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent@example.com");
        });
    }

    @Test
    public void testLoadUserByUsername_InvalidEmail() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("invalidemail");
        });
    }
}