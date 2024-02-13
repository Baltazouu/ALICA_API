    package org.alica.api.service;


    import org.alica.api.dao.Alumni;
    import org.alica.api.dao.Role;
    import org.alica.api.Enum.ERole;
    import org.alica.api.dto.request.SignInRequestDTO;
    import org.alica.api.dto.request.SignupRequestDTO;
    import org.alica.api.dto.response.ResponseAuthenticationDTO;
    import org.alica.api.exception.EmailExistsException;
    import org.alica.api.mapper.AlumniMapper;
    import org.alica.api.repository.AlumniRepository;
    import org.alica.api.repository.RoleRepository;
    import org.alica.api.security.jwt.JWTUtils;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.logging.Logger;

    @Service
    public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final AlumniRepository alumniRepository;

        private final RoleRepository roleRepository;

        private static final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;

        private final PasswordEncoder encoder;
        private final JWTUtils jwtUtils;

        Logger logger = Logger.getLogger(AuthService.class.getName());

        public AuthService(AlumniRepository alumniRepository,
                           AuthenticationManager authenticationManager,
                           JWTUtils jwtUtils,
                           PasswordEncoder encoder,
                           RoleRepository roleRepository) {

            this.alumniRepository = alumniRepository;
            this.authenticationManager = authenticationManager;
            this.roleRepository = roleRepository;
            this.jwtUtils = jwtUtils;
            this.encoder = encoder;



        }


        public void signUp(SignupRequestDTO signupRequestDTO) {
            if(alumniRepository.existsByEmail(signupRequestDTO.email())) {
                throw new EmailExistsException("Email already exists");
            }

            Role role = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new EmailExistsException("Role not found"));

            Alumni alumni = alumniMapper.mapToAlumni(signupRequestDTO);


            logger.info("Password : " + alumni.getPassword());
            alumni.setPassword(encoder.encode(alumni.getPassword()));
            logger.info("Password Encoded : " + alumni.getPassword());

            alumni.addRole(role);
            alumniRepository.save(alumni);
        }

        public ResponseAuthenticationDTO signIn(SignInRequestDTO signInRequestDTO) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequestDTO.email(), signInRequestDTO.password()));

            logger.info("Authentication : " + authentication.getPrincipal());
            Alumni alumni = alumniRepository.findByEmail(signInRequestDTO.email())
                    .orElseThrow(() -> new RuntimeException("User Not Found"));


            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseAuthenticationDTO.builder()
                    .email(signInRequestDTO.email())
                    .id(alumni.getId())
                    .token(jwtUtils.generateJwtToken(authentication))
                    .type("Bearer")
                    .roles(alumni.getRoles())
                    .build();
        }

}



