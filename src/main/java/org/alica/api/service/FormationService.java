package org.alica.api.service;
import jakarta.el.PropertyNotFoundException;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Formation;
import org.alica.api.Dto.request.RequestFormationDTO;
import org.alica.api.Dto.response.ResponseFormationDTO;
import org.alica.api.exception.UpdateObjectException;
import org.alica.api.mapper.FormationMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.FormationRepository;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class FormationService {

    private final FormationRepository formationRepository;
    private final AlumniRepository alumniRepository;

    private static final String FORMATION_NOT_FOUND = "Formation %s Not found !";

    private static final FormationMapper FORMATION_MAPPER = FormationMapper.INSTANCE;
    public FormationService(FormationRepository formationRepository,
                            AlumniRepository alumniRepository) {
        this.formationRepository = formationRepository;
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseFormationDTO> findAll(Pageable page) {

        Page<Formation> formations = formationRepository.findAll(page);

        return formations.map(FORMATION_MAPPER::mapToResponseResponseFormationDTO);
    }

    public ResponseFormationDTO findFormationById(UUID id) {
        Formation formation = formationRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(FORMATION_NOT_FOUND, id)));

        return FORMATION_MAPPER.mapToResponseResponseFormationDTO(formation);
    }

    public ResponseFormationDTO createFormation(RequestFormationDTO requestFormationDTO, UserDetailsImpl user) {

        Alumni alumni = alumniRepository.findById(user.getId()).orElseThrow(() -> new PropertyNotFoundException(String.format(FORMATION_NOT_FOUND, requestFormationDTO.alumniId())));
        Formation formation = FORMATION_MAPPER.mapToFormation(requestFormationDTO, alumni);

        return FORMATION_MAPPER.mapToResponseResponseFormationDTO(formationRepository.save(formation));
    }

    public ResponseFormationDTO updateFormation(RequestFormationDTO requestFormationDTO, UUID id,UserDetailsImpl user) {

        Formation formation = formationRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format(FORMATION_NOT_FOUND, id)));

        if(formation.getAlumni().getId() != user.getId()) throw new UpdateObjectException("You are not allowed to update this formation !");
        if (!alumniRepository.existsById(requestFormationDTO.alumniId())) throw new UpdateObjectException(String.format("Alumni %s Not found !", requestFormationDTO.alumniId()));

        formation.Update(requestFormationDTO);

        return FORMATION_MAPPER.mapToResponseResponseFormationDTO(formationRepository.save(formation));
    }

    public void deleteFormation(UUID id,UserDetailsImpl user) {
        Formation formation = formationRepository.findById(id).orElseThrow(()-> new PropertyNotFoundException(String.format(FORMATION_NOT_FOUND, id)));
        if(formation.getAlumni().getId() != user.getId()) throw new UpdateObjectException("You are not allowed to delete this formation !");
        formationRepository.deleteById(id);
    }

    public Page<ResponseFormationDTO> findFormationByAlumniId(UUID id, Pageable page) {
        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !", id)));
        return formationRepository.findByAlumni(alumni, page).map(FORMATION_MAPPER::mapToResponseResponseFormationDTO);
    }
}
