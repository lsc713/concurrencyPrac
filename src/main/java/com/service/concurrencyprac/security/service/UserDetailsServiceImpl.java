package com.service.concurrencyprac.security.service;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.repository.member.MemberRepository;
import com.service.concurrencyprac.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member userData = memberRepository.findByEmail(email)
            .orElseThrow(
                () -> new EntityNotFoundException("Not Found "+ email));

        return new UserDetailsImpl(userData);
    }
}
