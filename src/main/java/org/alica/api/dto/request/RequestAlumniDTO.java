package org.alica.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record RequestAlumniDTO(

        @Email @NotNull(message = "email est required") String email,
        @NotNull(message = "password is required") String password,
        @NotNull(message = "entryYear is required") String entryYear,
        @NotNull(message = "firstName is required") String firstName,
        @NotNull(message = "lastName is required") String lastName,
        String linkedinURL,
        String githubURL,
        String portfolioURL,
        String imageURL

) { }
