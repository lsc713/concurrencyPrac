package com.service.concurrencyprac.auth.repository.token;

import com.service.concurrencyprac.auth.domain.token.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList,Long> {

}
