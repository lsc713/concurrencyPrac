package com.service.concurrencyprac.auth.service.token;

public interface AuthService {

    String refreshAccessToken(String refreshToken
    );
}
