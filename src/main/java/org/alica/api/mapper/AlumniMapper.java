package org.alica.api.mapper;

import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Role;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Dto.request.SignupRequestDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.security.JWT.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlumniMapper {
    AlumniMapper INSTANCE = Mappers.getMapper(AlumniMapper.class);

    @Mapping(target = "id", ignore = true) // ignore bc generated with db
    @Mapping(target = "formations", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "offers", ignore = true)
    Alumni mapToAlumni(RequestAlumniDTO alumniDTO, Role role);

    @Mapping(target = "id", source = "id")
    ResponseAlumniDTO mapResponseAlumniDTO(Alumni alumni);


    @Mapping(target = "id", ignore = true) // ignore bc generated with db
    @Mapping(target = "formations", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "offers", ignore = true)
    Alumni mapToAlumni(SignupRequestDTO signupRequestDTO);


    UserDetailsImpl mapToUserDetailsImpl(Alumni alumni);
}
