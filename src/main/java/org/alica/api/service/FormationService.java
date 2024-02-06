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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class FormationService {

    private final FormationRepository formationRepository;
    private final AlumniRepository alumniRepository;

    private final FormationMapper FORMATION_MAPPER = FormationMapper.INSTANCE;
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
        Formation formation = formationRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Formation %s Not found !", id)));

        return FORMATION_MAPPER.mapToResponseResponseFormationDTO(formation);
    }

    public ResponseFormationDTO createFormation(RequestFormationDTO requestFormationDTO) {

        Alumni alumni = alumniRepository.findById(requestFormationDTO.alumniId()).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !", requestFormationDTO.alumniId())));
        Formation formation = FORMATION_MAPPER.mapToFormation(requestFormationDTO, alumni);

        System.out.println(formation.toString());

        return FORMATION_MAPPER.mapToResponseResponseFormationDTO(formationRepository.save(formation));
    }

    public ResponseFormationDTO updateFormation(RequestFormationDTO requestFormationDTO, UUID id) {

        Formation formation = formationRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format("Formation %s Not found !", id)));

        if (!alumniRepository.existsById(requestFormationDTO.alumniId())) throw new UpdateObjectException(String.format("Alumni %s Not found !", requestFormationDTO.alumniId()));
        formation.Update(requestFormationDTO);

        return FORMATION_MAPPER.mapToResponseResponseFormationDTO(formationRepository.save(formation));
    }

    public void deleteFormation(UUID id) {
        if(!formationRepository.existsById(id)) throw new PropertyNotFoundException(String.format("Formation %s Not found !", id));
        formationRepository.deleteById(id);
    }

    public Page<ResponseFormationDTO> findFormationByAlumniId(UUID id, Pageable page) {
        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !", id)));
        return formationRepository.findByAlumni(alumni, page).map(FORMATION_MAPPER::mapToResponseResponseFormationDTO);
    }
}
