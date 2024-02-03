package org.alica.api.Dto.response;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ResponseFormationDTO(

        UUID id,
        UUID alumniId,
        String name,
        String startDate,
        String endDate,
        String company,
        Boolean currentJob
) {
}
