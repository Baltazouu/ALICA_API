package org.alica.api.dto.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Validated
public record RequestExperienceDTO(

        @NotNull(message = "title is required")  @NotBlank String title,
        @NotNull(message = "start date is required") @NotBlank Date startDate,
        @NotNull(message = "end date is required") @NotBlank Date endDate,
        @NotNull(message = "company is required") @NotBlank String companyName,
        @NotNull(message = "boolean current is required") boolean isCurrent
) { }
