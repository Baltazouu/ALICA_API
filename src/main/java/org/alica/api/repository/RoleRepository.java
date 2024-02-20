package org.alica.api.repository;


import org.alica.api.dao.Role;
import org.alica.api.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

   // ERole findByName(String firstName);

    Optional<Role> findByName(ERole name);
}
