package com.service.concurrencyprac.api.repository.token;

import com.service.concurrencyprac.api.domain.token.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog,Long> {

}
