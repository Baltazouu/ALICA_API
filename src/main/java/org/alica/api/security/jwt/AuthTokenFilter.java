package org.alica.api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alica.api.exceptions.AuthenticateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        request.getCookies();

        try {
            if (request.getCookies() != null) {
                for (int i = 0; i < request.getCookies().length; i++) {
                    if (request.getCookies()[i].getName().equals("token")) {
                        jwt = request.getCookies()[i].getValue();
                    }
                }
            } else {
                jwt = parseJwt(request);
            }

            if (jwt != null) {
                if (jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Jeton expiré
                    Map<String, String> body = new HashMap<>();
                    body.put("message", "JWT token is expired");
                    body.put("path", request.getRequestURI());
                    body.put("status", HttpStatus.BAD_REQUEST.toString());
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("application/json");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                    return;
                }
            }
        } catch (UsernameNotFoundException e) {
            Map<String, String> body = new HashMap<>();
            body.put("message", e.getMessage());
            body.put("path", request.getRequestURI());
            body.put("status", HttpStatus.UNAUTHORIZED.toString());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            return;
        } catch (AuthenticateException e) {
            // Gérer l'exception spécifique liée à l'expiration du jeton
            Map<String, String> body = new HashMap<>();
            body.put("message", e.getMessage());
            body.put("path", request.getRequestURI());
            body.put("status", HttpStatus.BAD_REQUEST.toString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            return;
        } catch (Exception e) {
            throw new AuthenticateException("Invalid Token" + e.getMessage(), request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }





    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}