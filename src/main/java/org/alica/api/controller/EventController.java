package org.alica.api.controller;

import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestEventDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.dto.response.ResponseEventDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Page<ResponseEventDTO>> findAll(@PageableDefault Pageable page,@RequestParam(required = false) Optional<String> title) {
        return new ResponseEntity<>(this.eventService.findAll(page,title), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseEventDTO> findEventById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.eventService.findEventById(id), HttpStatus.OK);
    }

    @PreAuthorize("#event.alumniId() == authentication.principal.id")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEventDTO> createEvent(@Valid @RequestBody RequestEventDTO event) {
        return new ResponseEntity<>(this.eventService.createEvent(event), HttpStatus.CREATED);
    }

    @PreAuthorize("#event.alumniId() == authentication.principal.id")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEventDTO> updateEvent(@Valid @RequestBody RequestEventDTO event, @PathVariable UUID id) {
        return new ResponseEntity<>(this.eventService.updateEvent(event,id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID id) {
        this.eventService.deleteEvent(id, (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


    @GetMapping("/alumni/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResponseEventDTO>> findEventByAlumniId(@PathVariable UUID id, @PageableDefault Pageable page) {
        return new ResponseEntity<>(this.eventService.findEventByAlumniId(id, page), HttpStatus.OK);
    }


    @PreAuthorize("#userId == authentication.principal.id")
    @GetMapping("/subscribe/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@PathVariable UUID userId,@PathVariable UUID eventId) {
        this.eventService.subscribe(eventId,userId);
    }

    // user id is passed by user details
    @PreAuthorize("#userId == authentication.principal.id")
    @GetMapping("/unsubscribe/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(@PathVariable UUID userId, @PathVariable UUID eventId) {
        this.eventService.unsubscribe(eventId, userId);
    }

    // I choose list instead of page bc I think that there will not be a large
    // number of event subscribers to justify the use of a list
    @GetMapping("/subscribers/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseAlumniDTO>> findSubscribers(@PathVariable UUID eventId) {
        return new ResponseEntity<>(this.eventService.findSubscribers(eventId), HttpStatus.OK);
    }

}
