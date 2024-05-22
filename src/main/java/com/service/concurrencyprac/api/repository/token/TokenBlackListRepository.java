package com.service.concurrencyprac.api.repository.token;

import com.service.concurrencyprac.api.domain.token.TokenBlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackListEntity,Long> {

}
