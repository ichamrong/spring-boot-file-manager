package com.ichamrong.iprofileservice.repository;

import com.ichamrong.iprofileservice.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, String> {}
