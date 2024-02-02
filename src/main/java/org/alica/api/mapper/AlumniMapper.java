package org.alica.api.mapper;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlumniMapper {
    AlumniMapper INSTANCE = Mappers.getMapper(AlumniMapper.class);

    @Mapping(target = "id", ignore = true) // ignore bc generated with db
    Alumni mapToAlumni(RequestAlumniDTO alumniDTO);

    ResponseAlumniDTO mapResponseAlumniDTO(Alumni alumni);
}
