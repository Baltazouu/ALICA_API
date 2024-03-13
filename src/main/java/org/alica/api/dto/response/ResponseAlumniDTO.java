package org.alica.api.dto.response;

import lombok.*;
import org.alica.api.enums.ERole;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseAlumniDTO extends RepresentationModel<ResponseAlumniDTO> {

    private UUID id;
    private String email;
    private ERole role;
    private String entryYear;
    private String firstName;
    private String lastName;
    private String linkedinURL;
    private String githubURL;
    private String portfolioURL;
    private UUID imageId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseAlumniDTO that = (ResponseAlumniDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && role == that.role && Objects.equals(entryYear, that.entryYear) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(linkedinURL, that.linkedinURL) && Objects.equals(githubURL, that.githubURL) && Objects.equals(portfolioURL, that.portfolioURL) && Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, email, role, entryYear, firstName, lastName, linkedinURL, githubURL, portfolioURL, imageId);
    }

    @Override
    public String toString() {
        return "ResponseAlumniDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", entryYear='" + entryYear + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", linkedinURL='" + linkedinURL + '\'' +
                ", githubURL='" + githubURL + '\'' +
                ", portfolioURL='" + portfolioURL + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}


