package com.service.concurrencyprac.api.repository.token;

import com.service.concurrencyprac.api.domain.token.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken,Long> {

}
