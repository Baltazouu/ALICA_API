package org.alica.api.controller;


import org.alica.api.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/profile")
public class ProfileController {

    private final ProfileService profileService;

    ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

}
