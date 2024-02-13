package org.alica.api.dto.response;


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
