package org.alica.api.Dto.response;
import org.alica.api.Enum.Role;

import java.util.UUID;

public record ResponseAlumniDTO(
    UUID uuid,
    String email,
    String password,
    Role role,
    String entryYear,
    String firstName,
    String lastName,
    String linkedinURL,
    String githubURL,
    String portfolioURL,
    String imageId

) { }

