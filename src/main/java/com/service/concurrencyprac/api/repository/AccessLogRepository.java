package com.service.concurrencyprac.api.repository;

import com.service.concurrencyprac.api.domain.AccessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLogEntity,Long> {

}
