package com.service.concurrencyprac.auth.jwt;

import com.service.concurrencyprac.auth.domain.token.TokenBlackList.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtProvider {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;
    private final long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 1000L * 7; //7days

    @Value("${jwt.secret.key}")
    private String secretKey;
    private SecretKey key;
    private MacAlgorithm algorithm;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        algorithm = SIG.HS256;
    }

    public TokenPayload createTokenPayload(Long userId, TokenType tokenType) {
        Date date = new Date();
        long tokenTime =
            TokenType.ACCESS.equals(tokenType) ? ACCESS_TOKEN_TIME : REFRESH_TOKEN_TIME;
        return new TokenPayload(
            userId.toString(),
            UUID.randomUUID().toString(),
            date,
            new Date(date.getTime() + tokenTime)
        );
    }

    public String createToken(TokenPayload payload) {
        return BEARER_PREFIX + Jwts.builder()
            .subject(payload.getSub())
            .expiration(payload.getExpiredAt())
            .issuedAt(payload.getIat())
            .id(payload.getJti())
            .signWith(key, algorithm)
            .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid Token : " + e.getMessage());
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
