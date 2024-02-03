package org.alica.api.Dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Enum.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Alumnis")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;

    @ManyToMany(mappedBy = "alumnis", cascade = CascadeType.ALL)
    private Set<Event> events;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> event;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Article> articles;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Formation> formations;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "surName")
    private String lastName;

    @Column(name = "entryYear")
    private String entryYear;

    @Column(name = "linkedinUrl")
    private String linkedinURL;

    @Column(name = "githubUrl")
    private String githubURL;

    @Column(name = "portfolioUrl")
    private String portfolioURL;

    @Column(name  = "imageUrl")
    private String imageURL;


    public void update(RequestAlumniDTO dto){
        this.email = dto.email();
        this.password = dto.password();
        this.role = dto.role();
        this.entryYear = dto.entryYear();
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.linkedinURL = dto.linkedinURL();
        this.githubURL = dto.githubURL();
        this.portfolioURL = dto.portfolioURL();
        this.imageURL = dto.imageURL();
    }

    @Override
    public String toString() {
        return "Alumni{" +
                "id=" + id +
                //", offers=" + offers +
                //", events=" + events +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", entryYear='" + entryYear + '\'' +
                ", linkedinURL='" + linkedinURL + '\'' +
                ", githubURL='" + githubURL + '\'' +
                ", portfolioURL='" + portfolioURL + '\'' +
                ", imageId='" + imageURL + '\'' +
                '}';
    }
}
