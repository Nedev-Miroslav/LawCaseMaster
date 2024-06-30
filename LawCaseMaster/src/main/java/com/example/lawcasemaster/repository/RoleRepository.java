package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);

}
