package org.alica.api.repository;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findByOrganizer(Alumni alumni, Pageable page);

    Page<Event> findByTitleContaining(String title, Pageable page);

    List<Event> findByAlumnisContaining(Alumni alumni);


}
