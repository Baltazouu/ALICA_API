package org.alica.api.controllers;

import jakarta.validation.Valid;
import jakarta.ws.rs.Produces;
import org.alica.api.dto.response.RequestExperienceDTO;
import org.alica.api.dto.response.ResponseExperienceDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.ExperienceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/experiences")
public class ExperienceController {


    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService){
        this.experienceService = experienceService;
    }

    @GetMapping
    @Produces("application/json")
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseExperienceDTO> findAll(Pageable page, @RequestParam(required = false) Optional<String> title){
        return this.experienceService.findAll(page,title);
    }


    @GetMapping("/{id}")
    @Produces("application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseExperienceDTO findExperienceById(@PathVariable UUID id){
        return this.experienceService.findById(id);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/alumni")
    @Produces("application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseExperienceDTO> findExperienceByAlumniId(@AuthenticationPrincipal UserDetailsImpl user){
        return this.experienceService.findByAlumniId(user.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExperience(@PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl user){
        this.experienceService.deleteById(id,user.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseExperienceDTO createExperience(@Valid @RequestBody RequestExperienceDTO experienceDTO, @AuthenticationPrincipal UserDetailsImpl user){
        return experienceService.createExperience(experienceDTO,user.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateExperience(@Valid @RequestBody RequestExperienceDTO experienceDTO,@AuthenticationPrincipal UserDetailsImpl user){
        this.experienceService.update(experienceDTO,user.getId());
    }
}
