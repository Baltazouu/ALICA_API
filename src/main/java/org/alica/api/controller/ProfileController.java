package org.alica.api.controller;
import jakarta.validation.Valid;
import org.alica.api.Dto.request.RequestProfileDTO;
import org.alica.api.Dto.response.ResponseProfileDTO;
import org.alica.api.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseProfileDTO> findAll(@PageableDefault Pageable page){
        return this.profileService.findAll(page);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProfileDTO findById(@PathVariable UUID id){
        return this.profileService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProfileDTO createProfile(@Valid  @RequestBody RequestProfileDTO requestProfileDTO){
        return this.profileService.createProfile(requestProfileDTO);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProfileDTO updateProfile(@PathVariable UUID id,@Valid @RequestBody RequestProfileDTO requestProfileDTO){
        return this.profileService.updateProfile(requestProfileDTO,id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable UUID id){
        this.profileService.deleteProfile(id);
    }

}
