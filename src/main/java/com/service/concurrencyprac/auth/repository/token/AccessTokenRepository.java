package com.service.concurrencyprac.auth.repository.token;

import com.service.concurrencyprac.auth.domain.token.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken,Long> {

}
