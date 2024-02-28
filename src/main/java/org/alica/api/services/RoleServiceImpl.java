package org.alica.api.services;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.dao.Role;
import org.alica.api.enums.ERole;
import org.alica.api.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }
    public Role findByName(String name) {
        ERole name1 = ERole.valueOf(name);
        return roleRepository.findByName(name1).orElseThrow(() -> new PropertyNotFoundException("ERole not found"));
    }
}
