package org.alica.api.repository;

import org.alica.api.Dao.Alumni;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlumniRepository extends JpaRepository<Alumni, UUID> {

    @Query("select Alumni FROM Alumni ")
    Page<Alumni> findAll(Pageable p);

    Optional<Alumni> findByEmailAndPassword(String email, String password);

    Optional<Alumni> findByEmail(String email);


}
