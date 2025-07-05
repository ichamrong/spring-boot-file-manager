package com.ichamrong.iprofileservice.repository;

import com.ichamrong.iprofileservice.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {}
