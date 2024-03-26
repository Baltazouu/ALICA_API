package org.alica.api.dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.dto.request.RequestAlumniDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Alumni",uniqueConstraints = {@UniqueConstraint(name = "email_index", columnNames = {"email"})})
//@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alumni{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Offer> offers;

    @ManyToMany(mappedBy = "alumnis", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Event> events;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Event> eventOrganized;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Article> articles;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Formation> formations;

    @OneToMany(mappedBy = "alumni",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RefreshToken> refreshTokens;

//    @OneToOne(mappedBy = "alumni",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Image image; // pp image

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            })
    private Set<Role> roles = new HashSet<>();


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "sur_name")
    private String lastName;

    @Column(name = "entry_year")
    private String entryYear;

    @Column(name = "linkedin_url")
    private String linkedinURL;

    @Column(name = "github_url")
    private String githubURL;

    @Column(name = "portfolio_url")
    private String portfolioURL;

    @Column(name  = "image_id")
    private UUID imageId;


    public void update(RequestAlumniDTO dto){
        this.email = dto.email();
        //this.password = dto.password();
        this.entryYear = dto.entryYear();
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.linkedinURL = dto.linkedinURL();
        this.githubURL = dto.githubURL();
        this.portfolioURL = dto.portfolioURL();
        this.imageId = dto.imageId();
    }


    @Override
    public String toString() {
        return "Alumni{" +
                "id=" + id +
                //", offers=" + offers +
                //", events=" + events +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                //", Role size =" + roles.size() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", entryYear='" + entryYear + '\'' +
                ", linkedinURL='" + linkedinURL + '\'' +
                ", githubURL='" + githubURL + '\'' +
                ", portfolioURL='" + portfolioURL + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }


    public void addRole(Role role){
        if(this.roles == null)
            this.roles = new HashSet<>();
        this.roles.add(role);
    }


    @PrePersist
    public void removeExpiredRefreshToken(){
        if(this.refreshTokens != null){
            this.refreshTokens.removeIf(RefreshToken::isExpired);
        }
    }
}
