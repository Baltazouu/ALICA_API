package org.alica.api.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Alumni alumni;

    @Column(nullable = false)
    private UUID token;

    @Column(nullable = false)
    private Instant expiration;

    public boolean isExpired() {
        return Instant.now().isAfter(expiration);
    }
}
