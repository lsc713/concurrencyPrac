package com.service.concurrencyprac.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.Status;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;

import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsImplTest {

    private Member member;
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
            .email("test@example.com")
            .password("password")
            .name("Test Name")
            .nickName("TestNickName")
            .role(UserRole.USER)
            .build();

        userDetails = new UserDetailsImpl(member);
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(UserRole.USER.getDescription())));
    }

    @Test
    public void testGetMember() {
        Member memberFromUserDetails = userDetails.getMember();
        assertEquals(member, memberFromUserDetails);
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@example.com", userDetails.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    public void testGetUsername() {
        assertEquals("Test Name", userDetails.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void testIsDisabled() {
        member.disable();
        assertTrue(!userDetails.isEnabled());
        assertTrue(!userDetails.isAccountNonExpired());
        assertTrue(!userDetails.isAccountNonLocked());
    }
}