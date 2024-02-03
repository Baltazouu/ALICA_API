package org.alica.api.Dto.request;

import jakarta.validation.constraints.NotNull;
import org.alica.api.Enum.Contract;
import org.alica.api.Enum.Level;
import org.alica.api.Enum.Studies;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record RequestOfferDTO(

        @NotNull(message = "alumniId is required") UUID alumniId,
        @NotNull(message = "title is required") String title,
        @NotNull(message = "description is required") String description,
        @NotNull(message = "contract is required") Contract contract,
        @NotNull(message = "level is required") Level level,
        @NotNull(message = "city is required") String city,
        @NotNull(message = "company is required") String company,
        @NotNull(message = "jobDescription is required") String jobDescription,
        @NotNull(message = "studies is required") Studies studies,
        @NotNull(message = "contactEmail is required") String contactEmail,
        @NotNull(message = "image is required") String image,
        @NotNull(message = "experience is required") String experienceRequired,
        @NotNull(message = "contactNumber is required") int contactNumber,
        @NotNull(message = "companyURL is required") String companyURL

) { }
