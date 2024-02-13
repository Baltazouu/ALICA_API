package org.alica.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestFormationDTO;
import org.alica.api.dto.response.ResponseFormationDTO;
import org.alica.api.security.jwt.JWTUtils;
import org.alica.api.service.FormationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResponseFormationDTO>> findAll(@PageableDefault Pageable page) {
        return new ResponseEntity<>(this.formationService.findAll(page), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseFormationDTO> findFormationById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.formationService.findFormationById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseFormationDTO> createFormation(HttpServletRequest request, @Valid @RequestBody RequestFormationDTO formation) {

        return new ResponseEntity<>(this.formationService.createFormation(formation, JWTUtils.getUserAuthenticate(request)), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PreAuthorize("#formation.alumniId() == authentication.principal.id")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseFormationDTO> updateFormation(HttpServletRequest request,@Valid @RequestBody RequestFormationDTO formation, @PathVariable UUID id) {
        return new ResponseEntity<>(this.formationService.updateFormation(formation, id), HttpStatus.OK);
    }


    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFormation(HttpServletRequest request,@PathVariable UUID id) {
        this.formationService.deleteFormation(id,JWTUtils.getUserAuthenticate(request));
    }

    @GetMapping(value= "/alumni/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseFormationDTO> findFormationByAlumniId(@PathVariable UUID id, @PageableDefault Pageable page) {
        return this.formationService.findFormationByAlumniId(id, page);
    }

}
