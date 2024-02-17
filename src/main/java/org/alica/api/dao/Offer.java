package org.alica.api.dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.dto.request.RequestOfferDTO;
import org.alica.api.enums.EContract;
import org.alica.api.enums.ELevel;
import org.alica.api.enums.EStudies;

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

    @Column(name = "job_Description")
    private String jobDescription;

    @Column(name = "EContract")
    private EContract contract;

    @Column(name = "city")
    private String city;

    @Column(name = "EStudies")
    private EStudies studies;

    @Column(name = "experience_Required")
    private String experienceRequired;

    @Column(name = "ELevel")
    private ELevel level;

    @Column(name = "contact_Number")
    private int contactNumber;

    @Column(name = "contact_Email")
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
                ", EContract=" + contract +
                ", city='" + city + '\'' +
                ", EStudies=" + studies +
                ", experienceRequired='" + experienceRequired + '\'' +
                ", ELevel=" + level +
                ", contactNumber=" + contactNumber +
                ", contactEmail='" + contactEmail + '\'' +
                ", companyURL='" + companyURL + '\'' +
                '}';
    }

    public void update(RequestOfferDTO requestOfferDTO){
        this.title = requestOfferDTO.title() != null ? requestOfferDTO.title() : this.title;
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
        this.companyURL = requestOfferDTO.companyURL() != null ? requestOfferDTO.companyURL() : this.companyURL;
   }
}
