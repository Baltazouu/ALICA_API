package org.alica.api.controller;

import jakarta.validation.Valid;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.security.JWT.UserDetailsImpl;
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


//    @PreAuthorize("isAuthenticated()")
//    @PreAuthorize("hasRole(2)")
//
//
//    @PreAuthorize("#ERole == authentication.principal.ERole")

    @PreAuthorize("hasRole('USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseAlumniDTO> findAll(@PageableDefault Pageable page){
        return this.alumniService.findAll(page);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO findAlumniByEmail(@PathVariable UUID id){
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.alumniService.findAlumniById(details.getId());
    }


    //@RolesAllowed("ADMIN")
   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAlumniDTO createAlumni(@Valid @RequestBody RequestAlumniDTO alumniDTO){
        return this.alumniService.createAlumni(alumniDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO updateAlumni(@Valid @RequestBody RequestAlumniDTO alumniDTO, @PathVariable UUID id){
        return this.alumniService.updateAlumni(alumniDTO,id);

    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlumni(@PathVariable UUID id){
        this.alumniService.deleteAlumni(id);
    }

}
