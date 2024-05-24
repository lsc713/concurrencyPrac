package com.service.concurrencyprac.auth.jwt;

import java.util.Date;
import lombok.Getter;

@Getter
public class TokenPayload {

    private String sub;
    private String jti;
    private Date iat;
    private Date expiredAt;

    public TokenPayload(String sub, String jti, Date iat, Date expiredAt) {
        this.sub = sub;
        this.jti = jti;
        this.iat = iat;
        this.expiredAt = expiredAt;
    }
}
