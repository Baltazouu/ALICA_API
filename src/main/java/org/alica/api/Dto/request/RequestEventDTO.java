package org.alica.api.Dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Validated
@Builder
public record RequestEventDTO(

        @NotNull UUID alumniId,
        @NotNull String title,
        @NotNull String imgURL,
        @NotNull String description,
        @NotNull Date date,
        @NotNull String imageURL,
        @NotNull int nbMaxRegistrations
) {
}
