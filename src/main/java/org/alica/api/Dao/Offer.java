package org.alica.api.Dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.Dto.request.RequestOfferDTO;
import org.alica.api.Enum.Contract;
import org.alica.api.Enum.Level;
import org.alica.api.Enum.Studies;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Offer")
public class Offer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "alumni_id")
    private Alumni alumni;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "jobDescription")
    private String jobDescription;

    @Column(name = "contract")
    private Contract contract;

    @Column(name = "city")
    private String city;

    @Column(name = "studies")
    private Studies studies;

    @Column(name = "experienceRequired")
    private String experienceRequired;

    @Column(name = "level")
    private Level level;

    @Column(name = "contactNumber")
    private int contactNumber;

    @Column(name = "websiteURL")
    private String websiteURL;

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", contract=" + contract +
                ", city='" + city + '\'' +
                ", studies=" + studies +
                ", experienceRequired='" + experienceRequired + '\'' +
                ", level=" + level +
                ", contactNumber=" + contactNumber +
                ", websiteURL='" + websiteURL + '\'' +
                '}';
    }

   public void Update(RequestOfferDTO requestOfferDTO){
        this.name = requestOfferDTO.title() != null ? requestOfferDTO.title() : this.name;
        this.image = requestOfferDTO.image() != null ? requestOfferDTO.image() : this.image;
        this.company = requestOfferDTO.company() != null ? requestOfferDTO.company() : this.company;
        this.description = requestOfferDTO.description() != null ? requestOfferDTO.description() : this.description;
        this.jobDescription = requestOfferDTO.jobDescription() != null ? requestOfferDTO.jobDescription() : this.jobDescription;
        this.contract = requestOfferDTO.contract() != null ? requestOfferDTO.contract() : this.contract;
        this.city = requestOfferDTO.city() != null ? requestOfferDTO.city() : this.city;
        this.studies = requestOfferDTO.studies() != null ? requestOfferDTO.studies() : this.studies;
        this.experienceRequired = requestOfferDTO.experienceRequired() != null ? requestOfferDTO.experienceRequired() : this.experienceRequired;
        this.level = requestOfferDTO.level() != null ? requestOfferDTO.level() : this.level;
        this.contactNumber = requestOfferDTO.contactNumber() != 0 ? requestOfferDTO.contactNumber() : this.contactNumber;
        this.websiteURL = requestOfferDTO.websiteURL() != null ? requestOfferDTO.websiteURL() : this.websiteURL;
   }
}
