package org.alica.api.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    private String name;

    private String type;

    @Column(columnDefinition = "LONGTEXT")
    private String imageData;

    @OneToOne
    private Alumni alumni;
}
