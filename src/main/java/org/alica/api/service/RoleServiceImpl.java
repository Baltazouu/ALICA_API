package org.alica.api.service;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.Dao.Role;
import org.alica.api.Enum.ERole;
import org.alica.api.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }

    @Override
    public Role findByName(String name) {
        ERole name1 = ERole.valueOf(name);
        return roleRepository.findByName(name1).orElseThrow(() -> new PropertyNotFoundException("ERole not found"));
    }
}
