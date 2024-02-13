package org.alica.api.service;

import jakarta.transaction.Transactional;
import org.alica.api.dao.Alumni;
import org.alica.api.dao.Role;
import org.alica.api.Enum.ERole;
import org.alica.api.dto.request.RequestAlumniDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.exception.UpdateObjectException;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.RoleRepository;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.hibernate.PropertyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AlumniService implements UserDetailsService {

    private final AlumniRepository alumniRepository;

    private final RoleRepository roleRepository;

    private static final String  ALUMNI_NOT_FOUND = "Alumni %s Not found !";


    private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;
    AlumniService(AlumniRepository alumniRepository, RoleRepository roleRepository) {

        this.alumniRepository = alumniRepository;
        this.roleRepository = roleRepository;

    }


    public Page<ResponseAlumniDTO> findAll(Pageable p){

        Page<Alumni> alumnisPage =  this.alumniRepository.findAll(p);
        return alumnisPage.map(alumniMapper::mapResponseAlumniDTO);
    }


    public ResponseAlumniDTO findAlumniByEmail(String email){

        Alumni alumni = alumniRepository.findByEmail(email).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,email)));

        return alumniMapper.mapResponseAlumniDTO(alumni);

    }


    public ResponseAlumniDTO updateAlumni(RequestAlumniDTO alumniDTO, UUID id){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format(ALUMNI_NOT_FOUND,alumniDTO.email())));
        alumni.update(alumniDTO);
        return alumniMapper.mapResponseAlumniDTO(alumniRepository.save(alumni));
    }


    public ResponseAlumniDTO createAlumni(RequestAlumniDTO alumniDTO){

        Role role = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Alumni alumni = alumniMapper.mapToAlumni(alumniDTO,role);
        alumni = alumniRepository.save(alumni);

        return  alumniMapper.mapResponseAlumniDTO(alumni);
    }


    public ResponseAlumniDTO findAlumniById(UUID id){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,id)));

        return alumniMapper.mapResponseAlumniDTO(alumni);

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
