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
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.RefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private RefreshTokenRepository refreshTokenRepository;

    private AlumniRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AlumniRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAlumni(userRepository.findById(userId).orElseThrow(() -> new PropertyNotFoundException("User not found")));
        refreshToken.setExpiration(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiration().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        return refreshTokenRepository.deleteByAlumni(userRepository.findById(userId).orElseThrow(() -> new PropertyNotFoundException("User not found")));
    }
}