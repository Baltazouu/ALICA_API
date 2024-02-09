package org.alica.api.service;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.dao.Alumni;
import org.alica.api.dao.Event;
import org.alica.api.dto.request.RequestEventDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.dto.response.ResponseEventDTO;
import org.alica.api.exception.InsufficientPermissions;
import org.alica.api.exception.UpdateObjectException;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.mapper.EventMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.EventRepository;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AlumniRepository alumniRepository;
    
    private static final String EVENT_NOT_FOUND = "Event %s Not found !";

    private static final String ALUMNI_NOT_FOUND = "Alumni %s Not found !";

    private static final EventMapper eventMapper = EventMapper.INSTANCE;

    private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;

    public EventService(EventRepository eventRepository,
                        AlumniRepository alumniRepository) {
        this.eventRepository = eventRepository;
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseEventDTO> findAll(Pageable page, Optional<String> title) {
        Page<Event> events;

        if (title.isEmpty()) {
            events = this.eventRepository.findAll(page);
        } else {
            events = this.eventRepository.findByTitleContaining(title.get(), page);
        }
        return events.map(eventMapper::mapToResponseEventDTO);
    }


    public ResponseEventDTO findEventById(UUID id){
        Event event = eventRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(EVENT_NOT_FOUND,id)));

        return eventMapper.mapToResponseEventDTO(event);
    }

    public ResponseEventDTO createEvent(RequestEventDTO requestEventDTO, UserDetailsImpl user){

        Alumni alumni = alumniRepository.findById(user.getId()).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,requestEventDTO.alumniId())));

        Event event = eventMapper.mapToEvent(requestEventDTO,alumni);

        return eventMapper.mapToResponseEventDTO(eventRepository.save(event));
    }

    public ResponseEventDTO updateEvent(RequestEventDTO requestEventDTO, UUID id,UserDetailsImpl user){

        Event event = eventRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format(EVENT_NOT_FOUND,id)));

        if(user.getId() != event.getOrganizer().getId() || user.getAuthorities().stream().noneMatch(authority
                -> authority.getAuthority().equals("ROLE_USER") ||
                authority.getAuthority().equals("ROLE_MODERATOR")
                ))throw new InsufficientPermissions(String.format("You are not able to update this event %s !",id));

        event.Update(requestEventDTO);
        return eventMapper.mapToResponseEventDTO(eventRepository.save(event));
    }

    public void deleteEvent(UUID id, UserDetailsImpl user){

        Event event = eventRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(EVENT_NOT_FOUND,id)));

        if (user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_MODERATOR"))) {
            eventRepository.deleteById(id);
            return;
        }

        if(user.getId() != event.getOrganizer().getId()) throw new InsufficientPermissions(String.format("You are not the organizer of event %s !",id));
        eventRepository.deleteById(id);
    }


    public Page<ResponseEventDTO> findEventByAlumniId(UUID id, Pageable page){

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,id)));

        Page<Event> events = eventRepository.findByOrganizer(alumni,page);
        return events.map(eventMapper::mapToResponseEventDTO);
    }


    public List<ResponseAlumniDTO> findSubscribers(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException(String.format(EVENT_NOT_FOUND, id)));

        return event.getAlumnis().stream()
                .map(alumniMapper::mapResponseAlumniDTO)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }


    public void subscribe(UUID eventId, UUID alumniId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new PropertyNotFoundException(String.format(EVENT_NOT_FOUND,eventId)));

        if(event.isFull()) throw new RuntimeException(String.format("Event %s is full !",eventId));

        Alumni alumni = alumniRepository.findById(alumniId).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,alumniId)));

        if(event.getAlumnis().contains(alumni)) throw new RuntimeException(String.format("Alumni %s is already subscribed to event %s !",alumniId,eventId));
        if(alumni.getId().equals(event.getOrganizer().getId())) throw new RuntimeException(String.format("Alumni %s is the organizer of event %s !",alumniId,eventId));

        event.addAlumni(alumni);
        eventRepository.save(event);
    }

    public void unsubscribe(UUID eventId, UUID alumniId){
        if(!checkExistEvent(eventId)) throw new PropertyNotFoundException(String.format(EVENT_NOT_FOUND,eventId));
        if(!checkExistAlumni(alumniId)) throw new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,alumniId));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new PropertyNotFoundException(String.format(EVENT_NOT_FOUND,eventId)));
        Alumni alumni = alumniRepository.findById(alumniId).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,alumniId)));

        if(!event.getAlumnis().contains(alumni)) throw new RuntimeException(String.format("Alumni %s is not subscribed to event %s !",alumniId,eventId));

        event.removeAlumni(alumni);
        eventRepository.save(event);
    }


    public boolean checkExistEvent(UUID id){
        return eventRepository.existsById(id);
    }

    public boolean checkExistAlumni(UUID id){
        return alumniRepository.existsById(id);
    }

}
