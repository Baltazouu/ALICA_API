package org.alica.api.controllers;

import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestEventDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.dto.response.ResponseEventDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResponseEventDTO>> findAll(@PageableDefault Pageable page,@ApiParam(name = "title",required = false) @RequestParam(required = false) Optional<String> title) {
        return new ResponseEntity<>(this.eventService.findAll(page,title), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseEventDTO> findEventById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.eventService.findEventById(id), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEventDTO> createEvent(@Valid @RequestBody RequestEventDTO event,@AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(this.eventService.createEvent(event,user.getId()), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEventDTO> updateEvent(@Valid @RequestBody RequestEventDTO event,@AuthenticationPrincipal UserDetailsImpl user, @PathVariable UUID id) {
        return new ResponseEntity<>(this.eventService.updateEvent(event,id,user.getId()), HttpStatus.OK);
    }

    // gestion du delete dans le service à voir et modifier certainement
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/subscribe/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@PathVariable UUID eventId, @AuthenticationPrincipal UserDetailsImpl user) {
        this.eventService.subscribe(eventId, user.getId());
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unsubscribe/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe( @PathVariable UUID eventId,@AuthenticationPrincipal UserDetailsImpl user) {
        this.eventService.unsubscribe(eventId, user.getId());
    }

    @GetMapping("/subscribers/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResponseAlumniDTO>> findSubscribers(@PathVariable UUID eventId, @PageableDefault Pageable page) {
        return new ResponseEntity<>(this.eventService.findSubscribers(eventId,page), HttpStatus.OK);
    }

}
