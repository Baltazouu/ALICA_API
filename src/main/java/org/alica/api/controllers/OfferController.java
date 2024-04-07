package org.alica.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestOfferDTO;
import org.alica.api.dto.response.ResponseOfferDTO;
import org.alica.api.enums.EContract;
import org.alica.api.enums.ELevel;
import org.alica.api.enums.EStudies;
import org.alica.api.security.jwt.JWTUtils;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;

    OfferController(OfferService offerService){
        this.offerService = offerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseOfferDTO> findAll(@PageableDefault Pageable page,
                                          @RequestParam(required=false) Optional<ELevel> level,
                                          @RequestParam(required=false) Optional<EStudies> studies,
                                          @RequestParam(required=false) Optional<EContract> contract){
        return this.offerService.findAll(page,level,studies,contract);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseOfferDTO> findOfferById(@PathVariable UUID id)  {
         return new ResponseEntity<>(this.offerService.findOfferById(id), HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseOfferDTO> createOffer(@Valid @RequestBody RequestOfferDTO offer, @AuthenticationPrincipal UserDetailsImpl user){
        return new ResponseEntity<>(this.offerService.createOffer(offer,user.getId()), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseOfferDTO> updateOffer(@Valid @RequestBody RequestOfferDTO offer, @PathVariable UUID id,@AuthenticationPrincipal UserDetailsImpl user){
        return new ResponseEntity<>(this.offerService.updateOffer(offer,id,user.getId()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOffer(HttpServletRequest request,@PathVariable UUID id,@AuthenticationPrincipal UserDetailsImpl user){
        this.offerService.deleteOffer(id,user);
    }

    @GetMapping(value= "/alumni/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseOfferDTO> findOfferByAlumniId(@PathVariable UUID id, @PageableDefault Pageable page){
        return this.offerService.findOfferByAlumniId(id,page);
    }

}
