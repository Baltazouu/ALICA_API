package org.alica.api.Dto.request;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import java.util.UUID;

@Validated
public record RequestProfileDTO(

        @NotNull UUID alumniId,

        @NotNull String email,

        @NotNull String name,

        @NotNull String surname,

        String linkedinURL,

        String githubURL,

        String portfolioURL

) { }
