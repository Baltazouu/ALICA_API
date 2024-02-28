package org.alica.api.repository;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID>   {

    Optional<RefreshToken> findByToken(UUID token);

    @Modifying
    int deleteByAlumni(Alumni user);

    @Modifying
    void deleteByToken(UUID token);
}
