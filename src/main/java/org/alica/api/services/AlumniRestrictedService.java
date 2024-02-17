package org.alica.api.services;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.controllers.AlumniRestrictedController;
import org.alica.api.controllers.EventController;
import org.alica.api.controllers.OfferController;
import org.alica.api.dao.Alumni;
import org.alica.api.dto.response.ResponseAlumniRestrictedDTO;
import org.alica.api.mappers.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AlumniRestrictedService {

    private final AlumniRepository alumniRepository;

    private static final AlumniMapper alumniRestrictedMapper = AlumniMapper.INSTANCE;


    public AlumniRestrictedService(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }

    public void addHateoasLinks(ResponseAlumniRestrictedDTO alumniDTO) {
        Link selfLink = linkTo(methodOn(AlumniRestrictedController.class).findById(alumniDTO.getId())).withSelfRel();
        Link offersLink = linkTo(methodOn(OfferController.class).findOfferByAlumniId(alumniDTO.getId(), null)).withRel("offers");
        Link eventsLink = linkTo(methodOn(EventController.class).findEventByAlumniId(alumniDTO.getId(),null)).withRel("events");

        alumniDTO.add(selfLink);
        alumniDTO.add(offersLink);
        alumniDTO.add(eventsLink);
    }

    public Page<ResponseAlumniRestrictedDTO> findAll(Pageable page){
        Page<ResponseAlumniRestrictedDTO> responseAlumniRestrictedDTOS =  this.alumniRepository.findAll(page).map(alumniRestrictedMapper::mapResponseAlumniRestricted);

        responseAlumniRestrictedDTOS.forEach(this::addHateoasLinks);

        return responseAlumniRestrictedDTOS;

    }



    public ResponseAlumniRestrictedDTO findById(UUID id){
        Alumni alumni =  this.alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException("Alumni not found"));

        ResponseAlumniRestrictedDTO responseAlumniRestrictedDTO =  alumniRestrictedMapper.mapResponseAlumniRestricted(alumni);
        addHateoasLinks(responseAlumniRestrictedDTO);
        return responseAlumniRestrictedDTO;
    }
}
