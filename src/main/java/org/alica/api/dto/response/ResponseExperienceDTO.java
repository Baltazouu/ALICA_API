package org.alica.api.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExperienceDTO extends RepresentationModel<ResponseExperienceDTO> {

    private UUID id;
    private UUID alumniId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String companyName;
    private boolean isCurrent;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResponseExperienceDTO that = (ResponseExperienceDTO) o;
        return isCurrent == that.isCurrent && Objects.equals(id, that.id) && Objects.equals(alumniId, that.alumniId) && Objects.equals(title, that.title) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, alumniId, title, startDate, endDate);
    }

}
