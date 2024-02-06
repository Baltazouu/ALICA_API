package org.alica.api.service;


import org.alica.api.Dao.Alumni;
import org.alica.api.Dto.request.SignupRequestDTO;
import org.alica.api.exception.AuthenticateException;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    private final AlumniRepository alumniRepository;

    private final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;

    public AuthService(AlumniRepository alumniRepository,
                       AuthenticationManager authenticationManager) {

        this.alumniRepository = alumniRepository;
        this.authenticationManager = authenticationManager;

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
    }
}
