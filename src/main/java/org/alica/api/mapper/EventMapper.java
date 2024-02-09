package org.alica.api.mapper;

import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Event;
import org.alica.api.dto.request.RequestEventDTO;
import org.alica.api.dto.response.ResponseEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "alumniId", source = "organizer.id")
    ResponseEventDTO mapToResponseEventDTO(Event event);

    @Mapping(target = "organizer", source = "alumni")
    @Mapping(target = "imageURL", source = "requestEventDTO.imageURL")
    Event mapToEvent(RequestEventDTO requestEventDTO, Alumni alumni);
}
