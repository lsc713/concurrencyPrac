package com.service.concurrencyprac.auth.repository.token;

import com.service.concurrencyprac.auth.domain.token.TokenBlackList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    Optional<TokenBlackList> findByJti(String jti);

    List<TokenBlackList> findAllByExpiresAtLessThan(Date now);


}
