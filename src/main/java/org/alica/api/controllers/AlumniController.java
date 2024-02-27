package org.alica.api.controllers;

import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestAlumniDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.AlumniService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/alumnis")
public class AlumniController {

    private final AlumniService alumniService;

    AlumniController(AlumniService alumniService){
        this.alumniService = alumniService;
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseAlumniDTO> findAll(@PageableDefault Pageable page, @RequestParam(required = false) Optional<String> lastname){
        if(lastname.isPresent())
            return this.alumniService.findByLastName(lastname.get(),page);
        return this.alumniService.findAll(page);
    }


    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO findAlumniById(@PathVariable UUID id){
        return this.alumniService.findAlumniById(id);
    }


    @PreAuthorize("isAuthenticated()")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO updateAlumni(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody RequestAlumniDTO alumniDTO){
        return this.alumniService.updateAlumni(alumniDTO,user.getId());

    }

}
