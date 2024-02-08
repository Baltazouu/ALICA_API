package org.alica.api.Dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alica.api.Dto.request.RequestEventDTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Alumni organizer;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name="nbMaxRegistrations")
    private int nbMaxRegistrations;

    @ManyToMany
    @JoinTable(
            name = "event_subscription",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "alumni_id")
    )
    private Set<Alumni> alumnis = new HashSet<>();

    @Column(name = "nbRegistrations")
    private int nbRegistrations;

    @PrePersist
    public void updateNbRegistrations(){
        this.nbRegistrations = this.alumnis.size();
    }


    public void Update(RequestEventDTO requestEventDTO){
        this.title = requestEventDTO.title();
        this.imageURL = requestEventDTO.imageURL();
        this.description = requestEventDTO.description();
        this.date = requestEventDTO.date();
        this.nbMaxRegistrations = requestEventDTO.nbMaxRegistrations();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", organizer=" + organizer +
                ", imageURL='" + imageURL + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", nbMaxRegistrations=" + nbMaxRegistrations +
                '}';
    }

    public void addAlumni(Alumni alumni){

        this.alumnis.add(alumni);
        this.nbRegistrations ++;
    }

    public void removeAlumni(Alumni alumni){
        this.alumnis.remove(alumni);
        this.nbRegistrations --;
    }

    public boolean isFull(){
        return this.nbRegistrations == this.nbMaxRegistrations;
    }
}
