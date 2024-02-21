package org.alica.api.dto.response;


import lombok.Builder;
import org.alica.api.dao.Role;

import java.util.Set;
import java.util.UUID;

@Builder
public record ResponseAuthenticationDTO(
        String token,
        String type,
        String email,
        UUID id,
        String refreshToken,
        Set<Role> role
) { }
