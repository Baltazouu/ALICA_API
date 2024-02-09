package org.alica.api.service;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.dto.response.ResponseAlumniRestricted;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlumniRestrictedService {

    private final AlumniRepository alumniRepository;

    private static final AlumniMapper alumniRestrictedMapper = AlumniMapper.INSTANCE;


    public AlumniRestrictedService(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }


    public Page<ResponseAlumniRestricted> findAll(Pageable page){
        return this.alumniRepository.findAll(page).map(alumniRestrictedMapper::mapResponseAlumniRestricted);
    }

    public ResponseAlumniRestricted findById(UUID id){
        return this.alumniRepository.findById(id).map(alumniRestrictedMapper::mapResponseAlumniRestricted).orElseThrow(() -> new PropertyNotFoundException("Alumni not found"));
    }
}
