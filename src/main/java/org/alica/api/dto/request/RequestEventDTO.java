package org.alica.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Builder
@Validated
public record RequestEventDTO(

        @NotNull(message = "alumniId is required") UUID alumniId,
        @NotNull(message = "title is required") String title,
        @NotNull(message = "description is required") String description,
        @NotNull(message = "date is required") Date date,
        @NotNull(message = "imageURL is required") String imageURL,
        @NotNull(message = "nbMaxRegistrations is required") int nbMaxRegistrations
) {
}
