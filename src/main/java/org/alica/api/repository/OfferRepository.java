package org.alica.api.repository;


import org.alica.api.dao.Alumni;
import org.alica.api.dao.Offer;
import org.alica.api.enums.EContract;
import org.alica.api.enums.ELevel;
import org.alica.api.enums.EStudies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID>{

    Page<Offer> findByAlumni(Alumni alumni, Pageable pageable);


    Page<Offer> findByLevelAndStudiesAndContract(ELevel level, EStudies studies, EContract contract, Pageable pageable);

    Page<Offer> findByLevelAndStudies(ELevel level, EStudies studies, Pageable pageable);

    Page<Offer> findByLevelAndContract(ELevel level, EContract contract, Pageable pageable);

    Page<Offer> findByStudiesAndContract(EStudies studies, EContract contract, Pageable pageable);

    Page<Offer> findByLevel(ELevel level, Pageable pageable);

    Page<Offer> findByStudies(EStudies studies, Pageable pageable);

    Page<Offer> findByContract(EContract contract, Pageable pageable);
}
