package org.alica.api.repository;

import org.alica.api.dao.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID> {

    Page<Experience> findByTitleContaining(String title, Pageable p);
}
