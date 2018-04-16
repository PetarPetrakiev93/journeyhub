package com.softuni.journeyhub;

import com.softuni.journeyhub.users.entities.Role;
import com.softuni.journeyhub.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if(this.roleRepository.findAll().size() == 0) {
            seedRoles();
        }
    }

    private void seedRoles() {
        Role user = new Role();
        Role moderator = new Role();
        Role admin = new Role();
        user.setAuthority("ROLE_USER");
        moderator.setAuthority("ROLE_MODERATOR");
        admin.setAuthority("ROLE_ADMIN");
        this.roleRepository.save(user);
        this.roleRepository.save(moderator);
        this.roleRepository.save(admin);
    }
}
