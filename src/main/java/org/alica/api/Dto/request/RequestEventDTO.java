package org.alica.api.Dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Validated
@Builder
public record RequestEventDTO(

        @NotNull(message = "alumniId is required") UUID alumniId,
        @NotNull(message = "title is required") String title,
        @NotNull(message = "imgURL is required") String imgURL,
        @NotNull(message = "description is required") String description,
        @NotNull(message = "date is required") Date date,
        @NotNull(message = "imageURL is required") String imageURL,
        @NotNull(message = "nbMaxRegistrations is required") int nbMaxRegistrations
) {
}
