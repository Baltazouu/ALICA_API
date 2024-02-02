package org.alica.api.Dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.Dto.request.RequestAlumniDTO;
import org.alica.api.Enum.Role;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Alumni")
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

    @OneToMany(mappedBy = "alumni")
    private Set<Offer> offers;

    @OneToOne
    private Profile profile;

    @ManyToMany(mappedBy = "alumnis")
    private Set<Event> events;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role;


    public void update(RequestAlumniDTO dto){
        this.email = dto.email();
        this.password = dto.password();
        this.role = dto.role();
    }

    @Override
    public String toString() {
        return "Alumni{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
