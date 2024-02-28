package org.alica.api.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFormationDTO extends RepresentationModel<ResponseFormationDTO> {

    private UUID id;
    private UUID alumniId;
    private String name;
    private String startDate;
    private String endDate;
    private String company;
    private Boolean currentJob;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseFormationDTO that = (ResponseFormationDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(alumniId, that.alumniId) && Objects.equals(name, that.name) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(company, that.company) && Objects.equals(currentJob, that.currentJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, alumniId, name, startDate, endDate, company, currentJob);
    }
}

