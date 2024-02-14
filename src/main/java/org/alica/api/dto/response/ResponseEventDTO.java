package org.alica.api.dto.response;

import java.util.Date;
import java.util.UUID;

public record ResponseEventDTO(
        UUID id,
        UUID organizerId,
        String title,
        String imageURL,
        String description,
        Date date,
        int nbMaxRegistrations,

        int nbRegistrations
){ }
