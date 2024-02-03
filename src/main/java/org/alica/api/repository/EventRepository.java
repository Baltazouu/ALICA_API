package org.alica.api.repository;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findByOrganizer(Alumni alumni, Pageable page);
}
