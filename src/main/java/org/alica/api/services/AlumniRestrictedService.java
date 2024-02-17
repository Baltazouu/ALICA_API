package org.alica.api.services;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.dao.Alumni;
import org.alica.api.dto.response.ResponseAlumniRestricted;
import org.alica.api.mappers.AlumniMapper;
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
        Alumni alumni =  this.alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException("Alumni not found"));
       // return .map(alumniRestrictedMapper::mapResponseAlumniRestricted).orElseThrow(() -> new PropertyNotFoundException("Alumni not found"));
        System.out.println(alumni);
        return alumniRestrictedMapper.mapResponseAlumniRestricted(alumni);
    }
}
