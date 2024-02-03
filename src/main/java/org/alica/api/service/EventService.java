package org.alica.api.service;


import jakarta.el.PropertyNotFoundException;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Event;
import org.alica.api.Dto.request.RequestEventDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.Dto.response.ResponseEventDTO;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.mapper.EventMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AlumniRepository alumniRepository;

    private final EventMapper eventMapper = EventMapper.INSTANCE;

    private final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;

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

        Event event = eventMapper.mapToEvent(requestEventDTO,alumni);

        System.out.println(event.toString());

        return eventMapper.mapToResponseEventDTO(eventRepository.save(event));
    }

    public ResponseEventDTO updateEvent(RequestEventDTO requestEventDTO, UUID id){

        Event event = eventRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !",id)));

        if(!alumniRepository.existsById(requestEventDTO.alumniId())) throw new PropertyNotFoundException(String.format("Alumni %s Not found !",requestEventDTO.alumniId()));
        event.Update(requestEventDTO);
        return eventMapper.mapToResponseEventDTO(eventRepository.save(event));
    }

    public void deleteEvent(UUID id){
        if(!eventRepository.existsById(id)) throw new PropertyNotFoundException(String.format("Event %s Not found !",id));
        eventRepository.deleteById(id);
    }


    public Page<ResponseEventDTO> findEventByAlumniId(UUID id, Pageable page){
        if(!alumniRepository.existsById(id)) throw new PropertyNotFoundException(String.format("Alumni %s Not found !",id));
        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",id)));

        Page<Event> events = eventRepository.findByOrganizer(alumni,page);
        return events.map(eventMapper::mapToResponseEventDTO);
    }


    public List<ResponseAlumniDTO> findSubscribers(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !", id)));

        return event.getAlumnis().stream()
                .map(alumniMapper::mapResponseAlumniDTO)
                .collect(Collectors.toList());
    }


    public void Subscribe(UUID eventId,UUID alumniId){
        if((!checkExistEvent(eventId))) throw new PropertyNotFoundException(String.format("Event %s Not found !",eventId));
        if(!checkExistAlumni(alumniId)) throw new PropertyNotFoundException(String.format("Alumni %s Not found !",alumniId));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !",eventId)));

        if(event.isFull()) throw new RuntimeException(String.format("Event %s is full !",eventId));

        Alumni alumni = alumniRepository.findById(alumniId).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",alumniId)));

        if(event.getAlumnis().contains(alumni)) throw new RuntimeException(String.format("Alumni %s is already subscribed to event %s !",alumniId,eventId));
        if(alumni.getId().equals(event.getOrganizer().getId())) throw new RuntimeException(String.format("Alumni %s is the organizer of event %s !",alumniId,eventId));

        event.addAlumni(alumni);
        eventRepository.save(event);
    }

    public void Unsubscribe(UUID eventId,UUID alumniId){
        if(!checkExistEvent(eventId)) throw new PropertyNotFoundException(String.format("Event %s Not found !",eventId));
        if(!checkExistAlumni(alumniId)) throw new PropertyNotFoundException(String.format("Alumni %s Not found !",alumniId));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new PropertyNotFoundException(String.format("Event %s Not found !",eventId)));
        Alumni alumni = alumniRepository.findById(alumniId).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",alumniId)));

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
