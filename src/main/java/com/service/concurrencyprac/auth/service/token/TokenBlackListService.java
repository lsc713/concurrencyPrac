package com.service.concurrencyprac.auth.service.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface TokenBlackListService {


    void addToBlacklist(String accessToken, String refreshToken);

    boolean isTokenBlacklisted(String jti);

    void removeExpiredTokens();
}
