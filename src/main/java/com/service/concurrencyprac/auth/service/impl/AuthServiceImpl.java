package com.service.concurrencyprac.auth.service.impl;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.token.TokenBlackList.TokenType;
import com.service.concurrencyprac.auth.jwt.JwtProvider;
import com.service.concurrencyprac.auth.repository.member.MemberReader;
import com.service.concurrencyprac.auth.repository.member.MemberRepository;
import com.service.concurrencyprac.auth.service.token.AuthService;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.common.response.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private JwtProvider jwtProvider;
    private MemberReader memberReader;

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new InvalidParamException(ErrorCode.NOT_EXIST_TOKEN);
        }
        Claims info = jwtProvider.getUserInfoFromToken(refreshToken);

        Member member = memberReader.getMember(info.getId());
        return jwtProvider.createToken(jwtProvider.createTokenPayload(member.getEmail(), TokenType.ACCESS));
    }
}
