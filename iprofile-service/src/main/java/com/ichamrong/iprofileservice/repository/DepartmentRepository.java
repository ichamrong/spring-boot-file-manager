package com.ichamrong.iprofileservice.repository;

import com.ichamrong.iprofileservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}
