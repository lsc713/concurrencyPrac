package com.service.concurrencyprac.auth.repository.token;

import com.service.concurrencyprac.auth.domain.token.TokenBlackList;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    Optional<TokenBlackList> findByJti(String jti);

    @Query("delete from TokenBlackList tbl where tbl.expiresAt < :now")
    void deleteAllByExpiredTokens(@Param("now") Date now);


}
