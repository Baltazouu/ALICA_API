package org.alica.api.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Dto.response.ResponseAlumniDTO;
import org.alica.api.service.AlumniService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/alumnis")
public class AlumniController {


    private final AlumniService alumniService;

    AlumniController(AlumniService alumniService){
        this.alumniService = alumniService;
    }


   // @PreAuthorize("isAuthenticated()")
   // @PreAuthorize("hasRole(2)")

    //
   // @PreAuthorize("#ERole == authentication.principal.ERole")

    //@PreAuthorize("hasRole('USER')")
    @RolesAllowed("USER")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseAlumniDTO> findAll(@PageableDefault Pageable page){
        return this.alumniService.findAll(page);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO findAlumniByEmail(@PathVariable UUID id){
        return this.alumniService.findAlumniById(id);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAlumniDTO createAlumni(@Valid @RequestBody RequestAlumniDTO alumniDTO){
        return this.alumniService.createAlumni(alumniDTO);
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAlumniDTO updateAlumni(@Valid @RequestBody RequestAlumniDTO alumniDTO, @PathVariable UUID id){
        return this.alumniService.updateAlumni(alumniDTO,id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlumni(@PathVariable UUID id){
        this.alumniService.deleteAlumni(id);
    }

}
