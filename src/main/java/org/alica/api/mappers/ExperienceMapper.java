package org.alica.api.mappers;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.Experience;
import org.alica.api.dto.request.RequestExperienceDTO;
import org.alica.api.dto.response.ResponseExperienceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExperienceMapper {

    ExperienceMapper INSTANCE = Mappers.getMapper(ExperienceMapper.class);



    @Mapping(target = "alumniId", source = "alumni.id")
    @Mapping(target = "current", source = "experience.current")
    ResponseExperienceDTO mapToResponseExperienceDTO(Experience experience);


    @Mapping(target = "alumni", source = "alumni")
    @Mapping(target = "current", source = "requestExperienceDTO.current")
    Experience mapToExperience(RequestExperienceDTO requestExperienceDTO, Alumni alumni);
}
