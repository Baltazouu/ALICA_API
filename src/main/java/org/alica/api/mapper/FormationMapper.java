package org.alica.api.mapper;
import org.alica.api.dao.Alumni;
import org.alica.api.dao.Formation;
import org.alica.api.dto.request.RequestFormationDTO;
import org.alica.api.dto.response.ResponseFormationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FormationMapper {
    FormationMapper INSTANCE = Mappers.getMapper(FormationMapper.class);

    @Mapping(target = "alumniId", source = "alumni.id")
    ResponseFormationDTO mapToResponseResponseFormationDTO(Formation formation);

    @Mapping(target = "alumni", source = "alumni")
    Formation mapToFormation(RequestFormationDTO responseFormationDTO, Alumni alumni);
}
