package org.alica.api.Dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.dto.request.RequestOfferDTO;
import org.alica.api.Enum.EContract;
import org.alica.api.Enum.ELevel;
import org.alica.api.Enum.EStudies;

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

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "jobDescription")
    private String jobDescription;

    @Column(name = "EContract")
    private EContract EContract;

    @Column(name = "city")
    private String city;

    @Column(name = "EStudies")
    private EStudies EStudies;

    @Column(name = "experienceRequired")
    private String experienceRequired;

    @Column(name = "ELevel")
    private ELevel ELevel;

    @Column(name = "contactNumber")
    private int contactNumber;

    @Column(name = "contactEmail")
    private String contactEmail;

    @Column(name = "companyURL")
    private String companyURL;

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", alumni=" + alumni +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", EContract=" + EContract +
                ", city='" + city + '\'' +
                ", EStudies=" + EStudies +
                ", experienceRequired='" + experienceRequired + '\'' +
                ", ELevel=" + ELevel +
                ", contactNumber=" + contactNumber +
                ", contactEmail='" + contactEmail + '\'' +
                ", companyURL='" + companyURL + '\'' +
                '}';
    }

    public void Update(RequestOfferDTO requestOfferDTO){
        this.title = requestOfferDTO.title() != null ? requestOfferDTO.title() : this.title;
        this.image = requestOfferDTO.image() != null ? requestOfferDTO.image() : this.image;
        this.company = requestOfferDTO.company() != null ? requestOfferDTO.company() : this.company;
        this.description = requestOfferDTO.description() != null ? requestOfferDTO.description() : this.description;
        this.jobDescription = requestOfferDTO.jobDescription() != null ? requestOfferDTO.jobDescription() : this.jobDescription;
        this.EContract = requestOfferDTO.EContract() != null ? requestOfferDTO.EContract() : this.EContract;
        this.city = requestOfferDTO.city() != null ? requestOfferDTO.city() : this.city;
        this.EStudies = requestOfferDTO.EStudies() != null ? requestOfferDTO.EStudies() : this.EStudies;
        this.experienceRequired = requestOfferDTO.experienceRequired() != null ? requestOfferDTO.experienceRequired() : this.experienceRequired;
        this.ELevel = requestOfferDTO.ELevel() != null ? requestOfferDTO.ELevel() : this.ELevel;
        this.contactNumber = requestOfferDTO.contactNumber() != 0 ? requestOfferDTO.contactNumber() : this.contactNumber;
        this.companyURL = requestOfferDTO.companyURL() != null ? requestOfferDTO.companyURL() : this.companyURL;
   }
}
