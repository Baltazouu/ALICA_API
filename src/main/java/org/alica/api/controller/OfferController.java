package org.alica.api.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import org.alica.api.Dao.Offer;
import org.alica.api.Dto.request.RequestOfferDTO;
import org.alica.api.Dto.response.ResponseOfferDTO;
import org.alica.api.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    OfferController(OfferService offerService){
        this.offerService = offerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseOfferDTO> findAll(@PageableDefault Pageable page){
        return this.offerService.findAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseOfferDTO> findOfferById(@PathVariable UUID id){
         return new ResponseEntity<>(this.offerService.findOfferById(id), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseOfferDTO> createOffer(@Valid @RequestBody RequestOfferDTO offer){
        return new ResponseEntity<>(this.offerService.createOffer(offer), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseOfferDTO> updateOffer(@Valid @RequestBody RequestOfferDTO offer, @PathVariable UUID id){
        return new ResponseEntity<>(this.offerService.updateOffer(offer,id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOffer(@PathVariable UUID id){
        this.offerService.deleteOffer(id);
    }


    @GetMapping(value= "/alumni/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseOfferDTO> findOfferByAlumniId(@PathVariable UUID id, @PageableDefault Pageable page){
        return this.offerService.findOfferByAlumniId(id,page);
    }

}
