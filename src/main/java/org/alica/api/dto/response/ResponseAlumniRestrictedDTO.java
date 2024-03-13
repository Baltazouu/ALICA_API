package org.alica.api.dto.response;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAlumniRestrictedDTO extends RepresentationModel<ResponseAlumniRestrictedDTO> {

    private UUID id;
    private String firstName;
    private String lastName;
    private String linkedinURL;
    private UUID imageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseAlumniRestrictedDTO that = (ResponseAlumniRestrictedDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(linkedinURL, that.linkedinURL) && Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, lastName, linkedinURL, imageId);
    }
}

