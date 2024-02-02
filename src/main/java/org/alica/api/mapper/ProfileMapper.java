package org.alica.api.mapper;

import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Profile;
import org.alica.api.Dto.request.RequestProfileDTO;
import org.alica.api.Dto.response.ResponseProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(target = "id",ignore=true)
    @Mapping(target = "alumni",source = "alumni")
    @Mapping(target = "email",source = "requestProfileDTO.email")
    Profile mapToProfile(RequestProfileDTO requestProfileDTO, Alumni alumni);


    ResponseProfileDTO mapToResponseProfileDTO(Profile profile);

}
