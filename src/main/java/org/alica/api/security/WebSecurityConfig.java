package org.alica.api.security;

import org.alica.api.security.jwt.AuthEntryPointJWT;
import org.alica.api.security.jwt.AuthTokenFilter;
import org.alica.api.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableMethodSecurity( securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Value("${target.api.base.url}")
    private String targetApiUrl;

    private final AlumniService alumniService;

    private final AuthEntryPointJWT unauthorizedHandler;

    private final AuthTokenFilter authTokenFilter;

    @Autowired
    public WebSecurityConfig(AlumniService alumniService, AuthEntryPointJWT unauthorizedHandler, AuthTokenFilter authTokenFilter) {
        this.alumniService = alumniService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authTokenFilter = authTokenFilter;


    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return authTokenFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(alumniService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/alumni-restricted").permitAll()
                                .requestMatchers("/api/alumnis/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/alumnis/**").authenticated()
                                .requestMatchers("/api/events/**").permitAll()
                                .requestMatchers("/api/formations/**").permitAll()
                                .requestMatchers("/api/articles/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .anyRequest().permitAll()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

