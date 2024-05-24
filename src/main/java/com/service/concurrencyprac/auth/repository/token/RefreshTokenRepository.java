package com.service.concurrencyprac.auth.repository.token;

import com.service.concurrencyprac.auth.domain.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

}
