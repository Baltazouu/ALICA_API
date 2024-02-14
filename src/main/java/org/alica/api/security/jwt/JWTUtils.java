package org.alica.api.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.alica.api.exception.AuthenticateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;
    
    private final Logger logger = Logger.getLogger(JWTUtils.class.getName());


    public String generateJwtToken(Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("roles", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.info("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.info("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.info("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.info("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public static UserDetailsImpl getUserAuthenticate(HttpServletRequest request){
        try{
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new AuthenticateException("Authentication is required to access this resource",request.getRequestURI());
        }
    }

    public static UserDetailsImpl getUserAuthenticate(){
        try{
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new AuthenticateException("Authentication is required to access this resource","");
        }
    }

}
