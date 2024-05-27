package com.service.concurrencyprac.auth.service.impl;

import com.service.concurrencyprac.auth.domain.token.TokenBlackList;
import com.service.concurrencyprac.auth.domain.token.TokenBlackList.TokenType;
import com.service.concurrencyprac.auth.jwt.JwtProvider;
import com.service.concurrencyprac.auth.repository.token.TokenBlackListRepository;
import com.service.concurrencyprac.auth.service.token.TokenBlackListService;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlackListServiceImpl implements
    TokenBlackListService {

    private final JwtProvider jwtProvider;
    private final TokenBlackListRepository tokenBlackListRepository;

    @Override
    public void addToBlacklist(String accessToken, String refreshToken) {
        Claims accessClaims = jwtProvider.getUserInfoFromToken(accessToken);
        Claims refreshClaims = jwtProvider.getUserInfoFromToken(refreshToken);

        tokenBlackListRepository.save(new TokenBlackList(
            accessToken,
            accessClaims.getId(),
            TokenType.ACCESS,
            accessClaims.getExpiration()
        ));
        tokenBlackListRepository.save(new TokenBlackList(
            refreshToken,
            refreshClaims.getId(),
            TokenType.REFRESH,
            refreshClaims.getExpiration()
        ));
    }

    @Override
    public boolean isTokenBlacklisted(String jti) {
        Optional<TokenBlackList> tokenByJti = tokenBlackListRepository.findByJti(jti);
        return tokenByJti.isPresent();
    }

    @Override
    public void removeExpiredTokens() {
        List<TokenBlackList> expiredList =
            tokenBlackListRepository.findAllByExpiresAtLessThan(new Date());
        tokenBlackListRepository.deleteAllInBatch(expiredList);
    }
}
