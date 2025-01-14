package org.alica.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Builder
@Validated
public record RequestFormationDTO(
        @NotNull(message = "alumniId is required") UUID alumniId,
        @NotNull(message = "firstName is required") String name,
        @NotNull(message = "startDate is required") String startDate,
        @NotNull(message = "endDate is required") String endDate,
        @NotNull(message = "company is required") String company,
        @NotNull(message = "currentJob") Boolean currentJob
) { }
