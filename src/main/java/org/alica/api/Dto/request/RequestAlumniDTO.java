package org.alica.api.Dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.alica.api.Enum.Role;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record RequestAlumniDTO(

        @NotNull String email,
        @NotNull String password,
        @NotNull Role role,
        @NotNull String entryYear,
        @NotNull String firstName,
        @NotNull String lastName,

        String linkedinURL,

        String githubURL,

        String portfolioURL,

        String imageId

) { }
