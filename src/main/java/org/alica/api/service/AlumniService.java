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
import java.util.UUID;

@Service
public class AlumniService {

    private final AlumniRepository alumniRepository;

    private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;
    AlumniService(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
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

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni with email %s not found !",alumniDTO.email())));
        alumni.update(alumniDTO);
        return alumniMapper.mapResponseAlumniDTO(alumniRepository.save(alumni));
    }


    public ResponseAlumniDTO createAlumni(RequestAlumniDTO alumniDTO){

        Alumni alumni = alumniMapper.mapToAlumni(alumniDTO);
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
}
