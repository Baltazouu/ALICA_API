    package org.alica.api.service;


    import org.alica.api.Dao.Alumni;
    import org.alica.api.Dao.Role;
    import org.alica.api.Dto.request.SignInRequestDTO;
    import org.alica.api.Dto.request.SignupRequestDTO;
    import org.alica.api.Dto.response.ResponseAuthenticationDTO;
    import org.alica.api.Enum.ERole;
    import org.alica.api.mapper.AlumniMapper;
    import org.alica.api.repository.AlumniRepository;
    import org.alica.api.repository.RoleRepository;
    import org.alica.api.security.JWT.JWTUtils;
    import org.alica.api.security.JWT.TokenProvider;
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

        private final AuthenticationManager authenticationManager;
        private final AlumniRepository alumniRepository;

        private final RoleRepository roleRepository;

        private final AlumniMapper alumniMapper = AlumniMapper.INSTANCE;

        private final PasswordEncoder encoder;
        private final JWTUtils jwtUtils;

        private final TokenProvider jwtTokenUtil;

        public AuthService(AlumniRepository alumniRepository,
                           AuthenticationManager authenticationManager,
                           JWTUtils jwtUtils,
                           TokenProvider jwtTokenUtil,
                           PasswordEncoder encoder,
                           RoleRepository roleRepository) {

            this.alumniRepository = alumniRepository;
            this.authenticationManager = authenticationManager;
            this.roleRepository = roleRepository;
            this.jwtUtils = jwtUtils;
            this.encoder = encoder;
            this.jwtTokenUtil = jwtTokenUtil;



        }


        public void signUp(SignupRequestDTO signupRequestDTO) {
            if(alumniRepository.existsByEmail(signupRequestDTO.email())) {
                throw new RuntimeException("Email already exists");
            }

            Role role = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            Alumni alumni = alumniMapper.mapToAlumni(signupRequestDTO);
            // default role is user
            //alumni.setRoles(role);

            System.out.println("Password : " + alumni.getPassword());
            alumni.setPassword(encoder.encode(alumni.getPassword()));

            System.out.println("Password Encoded : " + alumni.getPassword());
    //        Authentication authentication = ;
    //
    //        SecurityContextHolder.getContext().setAuthentication(authentication);

            alumni.addRole(role);
            alumniRepository.save(alumni);
        }

        public ResponseAuthenticationDTO signIn(SignInRequestDTO signInRequestDTO) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequestDTO.email(), signInRequestDTO.password()));

            System.out.println("Authentication : " + authentication.getPrincipal());
            Alumni alumni = alumniRepository.findByEmail(signInRequestDTO.email())
                    .orElseThrow(() -> new RuntimeException("User Not Found"));



//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .toList();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);

            return ResponseAuthenticationDTO.builder()
                    .email(signInRequestDTO.email())
                    .token(jwtUtils.generateJwtToken(authentication))
                    .type("Bearer")
                    .roles(alumni.getRoles())
                    .build();
        }

}



