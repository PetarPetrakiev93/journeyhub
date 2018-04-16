package com.softuni.journeyhub.users.repositories;

import com.softuni.journeyhub.users.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByAuthority(String authority);
}
