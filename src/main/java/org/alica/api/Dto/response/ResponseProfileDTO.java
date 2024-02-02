package org.alica.api.Dto.response;
import java.util.UUID;

public record ResponseProfileDTO(

        UUID id,

        String email,
        String name,
        String surname,
        String linkedinURL,
        String githubURL,
        String portfolioURL
) {
}
