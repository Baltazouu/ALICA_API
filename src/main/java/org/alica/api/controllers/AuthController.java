package org.alica.api.controllers;


import jakarta.validation.Valid;
import org.alica.api.dto.request.SignInRequestDTO;
import org.alica.api.dto.request.SignupRequestDTO;
import org.alica.api.dto.response.ResponseAuthenticationDTO;
import org.alica.api.services.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${target.api.base.url}")
    private String targetApiUrl;

    private final AuthService authService;

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

}
