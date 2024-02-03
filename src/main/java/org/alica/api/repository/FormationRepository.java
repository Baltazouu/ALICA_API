package org.alica.api.repository;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Formation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FormationRepository extends JpaRepository<Formation, UUID>{

    Page<Formation> findByAlumni(Alumni alumni, Pageable pageable);
}
