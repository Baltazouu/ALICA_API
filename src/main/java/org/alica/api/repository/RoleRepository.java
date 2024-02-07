package org.alica.api.repository;


import org.alica.api.Dao.Role;
import org.alica.api.Enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

   // ERole findByName(String name);

    Optional<Role> findByName(ERole name);
}
