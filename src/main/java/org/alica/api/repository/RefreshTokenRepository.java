package org.alica.api.repository;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID>   {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByAlumni(Alumni user);
}
