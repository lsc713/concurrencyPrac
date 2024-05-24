package com.service.concurrencyprac.auth.repository;

import com.service.concurrencyprac.auth.domain.AccessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLogEntity, Long> {

}
