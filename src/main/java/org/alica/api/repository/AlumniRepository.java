package org.alica.api.repository;

import org.alica.api.dao.Alumni;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlumniRepository extends JpaRepository<Alumni, UUID> {

    Page<Alumni> findAll(Pageable pageable);

    Optional<Alumni> findByEmailAndPassword(String email, String password);

    Boolean existsByEmail(String email);

    Optional<Alumni> findByEmail(String email);

    Page<Alumni> findByLastName(String lastName, Pageable pageable);


}
