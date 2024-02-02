package org.alica.api.Dto.request;

import jakarta.validation.constraints.NotNull;
import org.alica.api.Enum.Contract;
import org.alica.api.Enum.Level;
import org.alica.api.Enum.Studies;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record RequestOfferDTO(

        @NotNull UUID alumniId,
        @NotNull String title,
        @NotNull String description,
        @NotNull Contract contract,
        @NotNull Level level,
        @NotNull String city,
        @NotNull String company,
        @NotNull String jobDescription,
        @NotNull Studies studies,
        @NotNull String email,
        @NotNull  String image,
        @NotNull String experienceRequired,
        @NotNull int contactNumber,
        String websiteURL

) { }
