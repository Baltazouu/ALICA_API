package org.alica.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@Service
@NoArgsConstructor
public class ResponseEventDTO extends RepresentationModel<ResponseAlumniDTO> {
        private UUID id;
        private UUID organizerId;
        private String title;
        private String imageId;
        private String description;
        private Date date;
        private int nbMaxRegistrations;
        private int nbRegistrations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseEventDTO that = (ResponseEventDTO) o;
        return nbMaxRegistrations == that.nbMaxRegistrations && nbRegistrations == that.nbRegistrations && Objects.equals(id, that.id) && Objects.equals(organizerId, that.organizerId) && Objects.equals(title, that.title) && Objects.equals(imageId, that.imageId) && Objects.equals(description, that.description) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, organizerId, title, imageId, description, date, nbMaxRegistrations, nbRegistrations);
    }
}
