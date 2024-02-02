package org.alica.api.service;

import org.alica.api.Dao.Alumni;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.hibernate.PropertyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlumniService {

    private final AlumniRepository alumniRepository;

    private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;
    AlumniService(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseAlumniDTO> findAll(Pageable page){

     Page<Alumni> alumnisPage =  this.alumniRepository.findAll(page);

        return alumnisPage.map(alumniMapper::mapResponseAlumniDTO);
    }

    public ResponseAlumniDTO findByMailAndPassword(String mail,String password){

        Alumni alumni =  alumniRepository.findByEmailAndPassword(mail,password).orElse(null);

        return alumniMapper.mapResponseAlumniDTO(alumni);
    }


    public ResponseAlumniDTO updateAlumni(RequestAlumniDTO alumniDTO){

        Alumni alumni = alumniRepository.findByEmail(alumniDTO.email()).orElseThrow(() -> new PropertyNotFoundException("Invalid Alumni Email"));

        alumni.update(alumniDTO);

        return alumniMapper.mapResponseAlumniDTO(alumniRepository.save(alumni));
    }


    public ResponseAlumniDTO createAlumni(RequestAlumniDTO alumniDTO){

        Alumni alumni = alumniMapper.mapToAlumni(alumniDTO);

        return  alumniMapper.mapResponseAlumniDTO(alumniRepository.save(alumni));
    }

}
