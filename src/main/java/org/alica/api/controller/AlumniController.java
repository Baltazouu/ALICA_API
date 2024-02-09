package org.alica.api.controller;

import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestAlumniDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.service.AlumniService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/alumnis")
public class AlumniController {


    private final AlumniService alumniService;


    AlumniController(AlumniService alumniService){
        this.alumniService = alumniService;
    }

    private UserDetailsImpl getUserDetails(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseAlumniDTO> findAll(@PageableDefault Pageable page){
        return this.alumniService.findAll(page);
    }


    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping(value = "/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO findAlumniByEmail(@PathVariable String email){
        return this.alumniService.findAlumniByEmail(email);
    }


    //@RolesAllowed("ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAlumniDTO createAlumni(@Valid @RequestBody RequestAlumniDTO alumniDTO){
        return this.alumniService.createAlumni(alumniDTO);
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO updateAlumni(@Valid @RequestBody RequestAlumniDTO alumniDTO, @PathVariable UUID id){
        return this.alumniService.updateAlumni(alumniDTO,id);

    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlumni(){
        this.alumniService.deleteAlumni(getUserDetails().getId());
    }

}
