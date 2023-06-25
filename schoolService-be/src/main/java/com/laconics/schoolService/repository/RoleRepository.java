package com.laconics.schoolService.repository;

import com.laconics.schoolService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
}
