package com.softuni.journeyhub.users.repositories;

import com.softuni.journeyhub.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getByUsername(String username);
    User getById(Long id);
}
