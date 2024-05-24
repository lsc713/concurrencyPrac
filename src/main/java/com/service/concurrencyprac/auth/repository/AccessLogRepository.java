package com.service.concurrencyprac.auth.repository;

import com.service.concurrencyprac.auth.domain.token.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}
