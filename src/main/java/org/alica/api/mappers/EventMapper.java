package org.alica.api.mappers;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.Event;
import org.alica.api.dto.request.RequestEventDTO;
import org.alica.api.dto.response.ResponseEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "organizerId", source = "organizer.id")
    ResponseEventDTO mapToResponseEventDTO(Event event);

    @Mapping(target = "organizer", source = "alumni")
    @Mapping(target = "imageURL", source = "requestEventDTO.imageURL")
    Event mapToEvent(RequestEventDTO requestEventDTO, Alumni alumni);
}
