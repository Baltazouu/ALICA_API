package org.alica.api.dto.response;


import lombok.Builder;
import org.alica.api.dao.Role;

import java.util.Set;

@Builder
public record ResponseAuthenticationDTO(
        String token,
        String type,
       // UUID id,
        String email,
        Set<Role> roles
) { }
