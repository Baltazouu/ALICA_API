package org.alica.api.services;

import jakarta.el.PropertyNotFoundException;
import jakarta.transaction.Transactional;
import org.alica.api.dao.RefreshToken;
import org.alica.api.exceptions.RefreshTokenException;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RefreshTokenService {
    @Value("${jwt.RefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final AlumniRepository userRepository;


    Logger logger = Logger.getLogger(RefreshTokenService.class.getName());

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AlumniRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(UUID token) {


        logger.info("All tokens : ");
        List<RefreshToken> tokens = refreshTokenRepository.findAll();


        System.out.println("All tokens : ");

        for (RefreshToken t : tokens) {
            logger.info(t.getToken().toString());
        }

        logger.info("Finding refresh token by token: " + token);
        logger.info("Refresh Token : "+refreshTokenRepository.findByToken(token));

        logger.info("Should be writted in console");

        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAlumni(userRepository.findById(userId).orElseThrow(() -> new PropertyNotFoundException("User not found")));
        refreshToken.setExpiration(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID());

        logger.info("Refresh token created for user: " + refreshToken.getAlumni().getEmail());
        logger.info("Refresh token expiration: " + refreshToken.getExpiration());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiration().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken().toString(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        return refreshTokenRepository.deleteByAlumni(userRepository.findById(userId).orElseThrow(() -> new PropertyNotFoundException("User not found")));
    }
}