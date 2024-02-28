package org.alica.api.services;

import jakarta.transaction.Transactional;
import org.alica.api.controllers.AlumniController;
import org.alica.api.controllers.EventController;
import org.alica.api.controllers.OfferController;
import org.alica.api.dao.Alumni;
import org.alica.api.dto.request.RequestAlumniDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.mappers.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.hibernate.PropertyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AlumniService implements UserDetailsService {

    private final AlumniRepository alumniRepository;
    private static final String  ALUMNI_NOT_FOUND = "Alumni %s Not found !";
    private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;
    AlumniService(AlumniRepository alumniRepository) {

        this.alumniRepository = alumniRepository;
    }

    public static void addHateoasLinks(ResponseAlumniDTO alumniDTO) {

        Link selfLink = linkTo(methodOn(AlumniController.class).findAlumniById(alumniDTO.getId())).withSelfRel();
        Link offersLink = linkTo(methodOn(OfferController.class).findOfferByAlumniId(alumniDTO.getId(), null)).withRel("offers");
        Link eventsLink = linkTo(methodOn(EventController.class).findEventByAlumniId(alumniDTO.getId(),null)).withRel("events");
        Link formationsLink = linkTo(methodOn(AlumniController.class).findAlumniById(alumniDTO.getId())).withRel("formations");
        alumniDTO.add(selfLink);
        alumniDTO.add(offersLink);
        alumniDTO.add(eventsLink);
        alumniDTO.add(formationsLink);
    }
    public Page<ResponseAlumniDTO> findAll(Pageable p){

        Page<Alumni> alumnisPage =  this.alumniRepository.findAll(p);

        Page<ResponseAlumniDTO> responseAlumniDTOS =  alumnisPage.map(alumniMapper::mapResponseAlumniDTO);

        for(ResponseAlumniDTO alumniDTO : responseAlumniDTOS){
            addHateoasLinks(alumniDTO);
        }
        return responseAlumniDTOS;
    }


    public ResponseAlumniDTO findAlumniByEmail(String email){

        Alumni alumni = alumniRepository.findByEmail(email).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,email)));

        ResponseAlumniDTO responseAlumniDTO = alumniMapper.mapResponseAlumniDTO(alumni);
        addHateoasLinks(responseAlumniDTO);
        return responseAlumniDTO;

    }


    public ResponseAlumniDTO updateAlumni(RequestAlumniDTO alumniDTO, UUID id){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,alumniDTO.email())));
        alumni.update(alumniDTO);
        ResponseAlumniDTO responseAlumniDTO = alumniMapper.mapResponseAlumniDTO(alumniRepository.save(alumni));
        addHateoasLinks(responseAlumniDTO);
        return responseAlumniDTO;
    }


    public ResponseAlumniDTO findAlumniById(UUID id){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,id)));

        ResponseAlumniDTO responseAlumniDTO =  alumniMapper.mapResponseAlumniDTO(alumni);
        addHateoasLinks(responseAlumniDTO);
        return responseAlumniDTO;
    }

    public Page<ResponseAlumniDTO> findByLastName(String lastName,Pageable p) {
        Page<Alumni> alumniPage = alumniRepository.findByLastNameContaining(lastName, p);
        Page<ResponseAlumniDTO> responseAlumniDTOS = alumniPage.map(alumniMapper::mapResponseAlumniDTO);
        for (ResponseAlumniDTO alumniDTO : responseAlumniDTOS) {
            addHateoasLinks(alumniDTO);
        }
        return responseAlumniDTOS;
    }

    public void deleteAlumni(UUID id){
        if(!alumniRepository.existsById(id)) throw new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,id));
        alumniRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumni alumni = alumniRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new UserDetailsImpl(alumni.getId(),alumni.getEmail(),alumni.getPassword(),getAuthority(alumni));
    }

    private Set<SimpleGrantedAuthority> getAuthority(Alumni user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

}
