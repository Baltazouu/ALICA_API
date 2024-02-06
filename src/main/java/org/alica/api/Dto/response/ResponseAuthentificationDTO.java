package org.alica.api.Dto.response;


import lombok.Builder;

import java.util.UUID;

@Builder
public record ResponseAuthentificationDTO(
        String token,
        String type,
        UUID id,
        String email,
        String role
) { }
