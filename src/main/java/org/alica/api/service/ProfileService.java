package org.alica.api.service;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Profile;
import org.alica.api.Dto.request.RequestProfileDTO;
import org.alica.api.Dto.response.ResponseProfileDTO;
import org.alica.api.mapper.ProfileMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.ProfileRepository;
import org.hibernate.PropertyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {


    private final ProfileRepository profileRepository;

    private final AlumniRepository alumniRepository;

    private static final ProfileMapper PROFILE_MAPPER = ProfileMapper.INSTANCE;


    ProfileService(ProfileRepository profileRepository,
                   AlumniRepository alumniRepository){
        this.profileRepository = profileRepository;
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseProfileDTO> findAll(Pageable page){

        Page<Profile> profilPage =  this.profileRepository.findAll(page);

        return profilPage.map(PROFILE_MAPPER::mapToResponseProfileDTO);
    }

    public ResponseProfileDTO findByEmail(String email){

        Profile profile = profileRepository.findByEmail(email).orElseThrow(() -> new PropertyNotFoundException(String.format("Invalid Profile Email %s",email)));

        return PROFILE_MAPPER.mapToResponseProfileDTO(profile);

    }


    public ResponseProfileDTO findById(UUID id){

        Profile profile = profileRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Profile Id %s Not Found !",id)));

        return PROFILE_MAPPER.mapToResponseProfileDTO(profile);

    }

    public ResponseProfileDTO updateProfile(RequestProfileDTO dto, UUID id){

        Profile profile = profileRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Profile Id %s Not Found ! ",id)));
        profile.update(dto);

        return PROFILE_MAPPER.mapToResponseProfileDTO(profileRepository.save(profile));

    }


    public ResponseProfileDTO createProfile(RequestProfileDTO requestProfileDTO){


        Alumni alumni = alumniRepository.findById(requestProfileDTO.alumniId()).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni Id %s not Found !", requestProfileDTO.alumniId())));
        Profile profile = PROFILE_MAPPER.mapToProfile(requestProfileDTO,alumni);

       // profile.setAlumni(alumni);

        return PROFILE_MAPPER.mapToResponseProfileDTO(profileRepository.save(profile));

    }
}
