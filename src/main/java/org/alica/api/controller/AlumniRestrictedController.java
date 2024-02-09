package org.alica.api.controller;

import org.alica.api.dto.response.ResponseAlumniRestricted;
import org.alica.api.service.AlumniRestrictedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/alumni-restricted")
public class AlumniRestrictedController {

    private final AlumniRestrictedService alumniRestrictedService;
    public AlumniRestrictedController(AlumniRestrictedService alumniRestrictedService){
        this.alumniRestrictedService = alumniRestrictedService;
    }

    @GetMapping
    public Page<ResponseAlumniRestricted> findALl(@PageableDefault Pageable page){
        return this.alumniRestrictedService.findAll(page);
    }


    @GetMapping("/{id}")
    public ResponseAlumniRestricted findById(@PathVariable UUID id) {
        return this.alumniRestrictedService.findById(id);
    }
}
