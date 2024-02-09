package org.alica.api.dto.response;

import java.util.UUID;

public record ResponseAlumniRestricted(
    UUID id,
    String firstName,
    String lastName,
    String linkedinURL,
    String imageURL
) { }
