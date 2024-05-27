package com.service.concurrencyprac.auth.service.token;

public interface TokenBlackListService {


    void addToBlacklist(String accessToken, String refreshToken);

    boolean isTokenBlacklisted(String jti);

    void removeExpiredTokens();
}
