package org.alica.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Builder
@Validated
public record RequestAlumniDTO(

        //@Email @NotNull(message = "email est required")
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE,message = "Invalid Email")String email,
        @NotNull(message = "password is required") String password,
        @NotNull(message = "entryYear is required") String entryYear,
        @NotNull(message = "firstName is required") String firstName,
        @NotNull(message = "lastName is required") String lastName,
        String linkedinURL,
        String githubURL,
        String portfolioURL,
        UUID imageId

) { }
