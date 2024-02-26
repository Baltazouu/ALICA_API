package org.alica.api.controllers;


import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestRefreshDTO;
import org.alica.api.dto.request.SignInRequestDTO;
import org.alica.api.dto.request.SignupRequestDTO;
import org.alica.api.dto.response.ResponseAuthenticationDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${target.api.base.url}")
    private String targetApiUrl;

    private final AuthService authService;

    Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignupRequestDTO signupRequestDTO){
        authService.signUp(signupRequestDTO);
    }
    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public ResponseAuthenticationDTO signIn(@Valid @RequestBody SignInRequestDTO signInRequestDTO){
        return authService.signIn(signInRequestDTO);
    }

    @PostMapping("/refresh")
    public ResponseAuthenticationDTO refresh(@Validated @RequestBody RequestRefreshDTO requestRefreshDTO){
        return authService.refreshToken(requestRefreshDTO.refreshToken());
    }


    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal UserDetailsImpl user){
        logger.info("Deleting user with id: " + user.getId());
        authService.delete(user.getId());
    }

}
