package org.alica.api.dto.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Validated
public record RequestExperienceDTO(

        @NotNull(message = "title is required")  String title,
        @NotNull(message = "start date is required") Date startDate,
        @NotNull(message = "end date is required") Date endDate,
        @NotNull(message = "company is required") String companyName,
        @NotNull(message = "boolean current is required") boolean isCurrent
) { }
