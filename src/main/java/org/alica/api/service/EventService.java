package org.alica.api.service;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Event;
import org.alica.api.Dto.request.RequestEventDTO;
import org.alica.api.Dto.response.ResponseEventDTO;
import org.alica.api.mapper.EventMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AlumniRepository alumniRepository;

    private final EventMapper eventMapper = EventMapper.INSTANCE;

    public EventService(EventRepository eventRepository,
                        AlumniRepository alumniRepository) {
        this.eventRepository = eventRepository;
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseEventDTO> findAll(Pageable page){

        Page<Event> events = this.eventRepository.findAll(page);

        return events.map(eventMapper::mapToResponseEventDTO);

    }

    public ResponseEventDTO findEventById(UUID id){
        Event event = eventRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !",id)));

        return eventMapper.mapToResponseEventDTO(event);
    }

    public ResponseEventDTO createEvent(RequestEventDTO requestEventDTO){

        Alumni alumni = alumniRepository.findById(requestEventDTO.alumniId()).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",requestEventDTO.alumniId())));

        Event event = eventMapper.mapToEvent(requestEventDTO,alumni.getId());

        return eventMapper.mapToResponseEventDTO(eventRepository.save(event));
    }

    public ResponseEventDTO updateEvent(RequestEventDTO requestEventDTO, UUID id){

        Event event = eventRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !",id)));

        Alumni alumni = alumniRepository.findById(requestEventDTO.alumniId()).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",requestEventDTO.alumniId())));

        event.Update(requestEventDTO);

        return eventMapper.mapToResponseEventDTO(eventRepository.save(event));
    }

    public void deleteEvent(UUID id){
        Event event = eventRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !",id)));
        eventRepository.deleteById(id);
    }

}
