package com.service.concurrencyprac.api.repository.token;

import com.service.concurrencyprac.api.domain.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

}
