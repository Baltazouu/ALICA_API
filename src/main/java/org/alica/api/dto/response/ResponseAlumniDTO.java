package org.alica.api.dto.response;
import org.alica.api.Enum.ERole;

import java.util.UUID;

public record ResponseAlumniDTO(
    UUID id,
    String email,
    ERole ERole,
    String entryYear,
    String firstName,
    String lastName,
    String linkedinURL,
    String githubURL,
    String portfolioURL,
    String imageURL

) { }

