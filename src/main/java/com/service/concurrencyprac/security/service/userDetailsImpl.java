package com.service.concurrencyprac.security.service;

import com.service.concurrencyprac.api.domain.member.Member;
import com.service.concurrencyprac.api.domain.member.Member.Status;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class userDetailsImpl implements UserDetails {

    private Member userData;

    public userDetailsImpl(Member userData) {
        this.userData = userData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(userData.getRole());
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userData.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userData.getStatus() == Status.ACTIVATE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userData.getStatus() == Status.ACTIVATE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userData.getStatus() == Status.ACTIVATE;
    }
}
