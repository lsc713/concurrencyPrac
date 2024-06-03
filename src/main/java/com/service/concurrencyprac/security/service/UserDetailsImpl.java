package com.service.concurrencyprac.security.service;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.Status;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = member.getRole();
        String roleDescription = role.getDescription();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleDescription);
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(simpleGrantedAuthority);
        return collection;
    }

    public Member getMember() {
        return member;
    }

    public String getEmail() {
        return member.getEmail();
    }

    public String getNickname() {
        return member.getNickName();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return member.getStatus() == Status.ACTIVATE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getStatus() == Status.ACTIVATE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.getStatus() == Status.ACTIVATE;
    }
}
