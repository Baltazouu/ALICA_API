package org.alica.api.Dto.response;
import org.alica.api.Enum.Role;

import java.util.UUID;

public record ResponseAlumniDTO(
    UUID uuid,
    String email,
    String password,
    Role role

) { }

