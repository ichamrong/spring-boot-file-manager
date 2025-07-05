package com.ichamrong.iprofileservice.repository;

import com.ichamrong.iprofileservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
