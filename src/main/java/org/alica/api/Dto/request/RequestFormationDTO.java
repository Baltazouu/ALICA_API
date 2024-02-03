package org.alica.api.Dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.aspectj.weaver.ast.Not;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Builder
public record RequestFormationDTO(
        @NotNull(message = "alumniId is required") UUID alumniId,
        @NotNull(message = "name is required") String name,
        @NotNull(message = "startDate is required") String startDate,
        @NotNull(message = "endDate is required") String endDate,
        @NotNull(message = "company is required") String company,
        @NotNull(message = "currentJob") Boolean currentJob
) { }
