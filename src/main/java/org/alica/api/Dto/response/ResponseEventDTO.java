package org.alica.api.Dto.response;

import java.util.Date;
import java.util.UUID;

public record ResponseEventDTO(
        UUID id,
        String title,
        String imageURL,
        String description,
        Date date,
        int nbMaxRegistrations
){ }
