package org.alica.api.config;


import org.alica.api.dao.Role;
import org.alica.api.enums.ERole;
import org.alica.api.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRole implements ApplicationRunner {

    //private static final int ROLE_NUMBER = 3;

    private final RoleRepository roleRepository;

    public InitRole(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {


        if(roleRepository.findByName(ERole.USER).isEmpty()) {
            roleRepository.save(Role.builder().name(ERole.USER).build());
        }
        if(roleRepository.findByName(ERole.MODERATOR).isEmpty()) {
            roleRepository.save(Role.builder().name(ERole.MODERATOR).build());
        }
        if(roleRepository.findByName(ERole.ADMIN).isEmpty()) {
            roleRepository.save(Role.builder().name(ERole.ADMIN).build());
        }

    }
}
