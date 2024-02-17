package org.alica.api.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseOfferDTO extends RepresentationModel<ResponseOfferDTO> {

    private UUID id;

    private UUID alumniId;

    private String title;

    private String description;

    private String jobDescription;

    private String experienceRequired;

    private String city;

    private org.alica.api.enums.EContract contract;

    private org.alica.api.enums.EStudies studies;

    private org.alica.api.enums.ELevel level;

    private String company;

    private String contactEmail;

    private String contactNumber;

    private String companyURL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResponseOfferDTO that = (ResponseOfferDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(alumniId, that.alumniId) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(jobDescription, that.jobDescription) && Objects.equals(experienceRequired, that.experienceRequired) && Objects.equals(city, that.city) && contract == that.contract && studies == that.studies && level == that.level && Objects.equals(company, that.company) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(contactNumber, that.contactNumber) && Objects.equals(companyURL, that.companyURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, alumniId, title, description, jobDescription, experienceRequired, city, contract, studies, level, company, contactEmail, contactNumber, companyURL);
    }
}
