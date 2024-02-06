package org.alica.api.service;


import org.alica.api.Dao.Alumni;
import org.alica.api.Dto.request.SignupRequestDTO;
import org.alica.api.exception.AuthenticateException;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.security.JWT.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${target.api.base.url}")
    private String targetApiUrl;

    AuthenticationManager authenticationManager;
    private final AlumniRepository alumniRepository;

    private final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;

    PasswordEncoder encoder;
    JWTUtils jwtUtils;


    public AuthService(AlumniRepository alumniRepository,
                       AuthenticationManager authenticationManager,
                       JWTUtils jwtUtils,
                       PasswordEncoder encoder) {

        this.alumniRepository = alumniRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;


    }


    public void signUp(SignupRequestDTO signupRequestDTO) {
        if(alumniRepository.existsByEmail(signupRequestDTO.email())) {
            throw new RuntimeException("Email already exists");
        }

       // Authentication authentication = ;

        alumniRepository.save(alumniMapper.mapToAlumni(signupRequestDTO));
    }

    public void signIn(SignupRequestDTO signupRequestDTO) {
        Alumni alumni = alumniRepository.findByEmailAndPassword(signupRequestDTO.email(), signupRequestDTO.password()).orElseThrow(() -> new AuthenticateException("Invalid Email or Password !"));
        //alumniRepository.save(alumniMapper.mapToAlumni(signupRequestDTO));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(alumni.getEmail(),alumni.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String JWT = JWTUtils.generateJwtToken(authentication);
    }
}
