package org.alica.api.service;

import jakarta.transaction.Transactional;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Role;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.Enum.ERole;
import org.alica.api.exception.UpdateObjectException;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.RoleRepository;
import org.alica.api.security.JWT.UserDetailsImpl;
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

    private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;
    AlumniService(AlumniRepository alumniRepository, RoleRepository roleRepository) {

        this.alumniRepository = alumniRepository;
        this.roleRepository = roleRepository;

    }


    public Page<ResponseAlumniDTO> findAll(Pageable p){

        Page<Alumni> alumnisPage =  this.alumniRepository.findAll(p);
        return alumnisPage.map(alumniMapper::mapResponseAlumniDTO);
    }

    public ResponseAlumniDTO findByMailAndPassword(String mail,String password){

        Alumni alumni =  alumniRepository.findByEmailAndPassword(mail,password).orElse(null);

        return alumniMapper.mapResponseAlumniDTO(alumni);
    }


    public ResponseAlumniDTO updateAlumni(RequestAlumniDTO alumniDTO, UUID id){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format("Alumni with email %s not found !",alumniDTO.email())));
        alumni.update(alumniDTO);
        return alumniMapper.mapResponseAlumniDTO(alumniRepository.save(alumni));
    }


    public ResponseAlumniDTO createAlumni(RequestAlumniDTO alumniDTO){

        Role role = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Alumni alumni = alumniMapper.mapToAlumni(alumniDTO,role);
        alumni = alumniRepository.save(alumni);
        System.out.println(alumni.toString());

        return  alumniMapper.mapResponseAlumniDTO(alumni);
    }


    public ResponseAlumniDTO findAlumniById(UUID id){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",id)));

        return alumniMapper.mapResponseAlumniDTO(alumni);

    }

    public void deleteAlumni(UUID id){
        if(!alumniRepository.existsById(id)) throw new PropertyNotFoundException(String.format("Alumni %s Not found !",id));
        alumniRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumni alumni = alumniRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        UserDetailsImpl userDetails = new UserDetailsImpl(alumni.getId(),alumni.getEmail(),alumni.getPassword(),getAuthority(alumni));

        return userDetails;
    }

    private Set<SimpleGrantedAuthority> getAuthority(Alumni user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

}
