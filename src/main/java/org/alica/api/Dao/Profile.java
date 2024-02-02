package org.alica.api.Dao;
import jakarta.persistence.*;
import lombok.*;
import org.alica.api.Dto.request.RequestProfileDTO;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @OneToOne
    private  Alumni alumni;


    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "surName")
    private String lastName;

    @Column(name = "linkedinUrl")
    private String linkedinURL;

    @Column(name = "githubUrl")
    private String githubURL;

    @Column(name = "portfolioUrl")
    private String portfolioURL;


    public void update(RequestProfileDTO dto){
        this.email = dto.email() != null ? dto.email() : this.email;
        this.firstName = dto.email() != null ? dto.email() : this.firstName;
        this.lastName = dto.surname() != null ? dto.surname() : this.lastName;
        this.linkedinURL = dto.linkedinURL() != null ? dto.linkedinURL() : this.lastName;
        this.githubURL = dto.githubURL() != null ? dto.githubURL() : this.githubURL;
        this.portfolioURL = dto.githubURL() != null ? dto.portfolioURL() : this.portfolioURL;
    }
}
