package com.service.concurrencyprac.api.repository.token;

import com.service.concurrencyprac.api.domain.token.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {

}
