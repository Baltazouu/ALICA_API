package org.alica.api.controller;

import jakarta.validation.Valid;
import org.alica.api.Dto.request.RequestEventDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.Dto.response.ResponseEventDTO;
import org.alica.api.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResponseEventDTO>> findAll(@PageableDefault Pageable page) {
        return new ResponseEntity<>(this.eventService.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseEventDTO> findEventById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.eventService.findEventById(id), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseEventDTO> createEvent(@Valid @RequestBody RequestEventDTO event) {
        return new ResponseEntity<>(this.eventService.createEvent(event), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseEventDTO> updateEvent(@Valid @RequestBody RequestEventDTO event, @PathVariable UUID id) {
        return new ResponseEntity<>(this.eventService.updateEvent(event, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID id) {
        this.eventService.deleteEvent(id);
    }


    @GetMapping("/alumni/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResponseEventDTO>> findEventByAlumniId(@PathVariable UUID id, @PageableDefault Pageable page) {
        return new ResponseEntity<>(this.eventService.findEventByAlumniId(id, page), HttpStatus.OK);
    }

    @GetMapping("/subscribe/{eventId}/alumni/{alumniId}")
    @ResponseStatus(HttpStatus.OK)
    public void Subscribe(@PathVariable UUID eventId, @PathVariable UUID alumniId) {
        this.eventService.Subscribe(eventId, alumniId);
    }

    @GetMapping("/unsubscribe/{eventId}/alumni/{alumniId}")
    @ResponseStatus(HttpStatus.OK)
    public void Unsubscribe(@PathVariable UUID eventId, @PathVariable UUID alumniId) {
        this.eventService.Unsubscribe(eventId, alumniId);
    }

    // I choose list instead of page bc i think that there will not be a large
    // number of event subscribers to justify the use of a list
    @GetMapping("/subscribers/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseAlumniDTO>> findSubscribers(@PathVariable UUID eventId) {
        return new ResponseEntity<>(this.eventService.findSubscribers(eventId), HttpStatus.OK);
    }

}
