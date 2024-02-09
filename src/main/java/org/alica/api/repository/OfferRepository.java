package org.alica.api.repository;


import org.alica.api.dao.Alumni;
import org.alica.api.dao.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID>{

    Page<Offer> findByAlumni(Alumni alumni, Pageable pageable);

}
