package org.alica.api.dto.request;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import java.util.Date;

@Validated
public record RequestExperienceDTO(

        @NotNull(message = "title is required")  String title,
        @NotNull(message = "start date is required") Date startDate,
        Date endDate,
        @NotNull(message = "company is required") String companyName,
        @NotNull(message = "boolean current is required") boolean current
) { }
