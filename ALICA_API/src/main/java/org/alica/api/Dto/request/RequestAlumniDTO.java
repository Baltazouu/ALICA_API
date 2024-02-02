package org.alica.api.Dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.alica.api.Enum.Role;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record RequestAlumniDTO(

        @NotNull String email,

        @NotNull String password,

        @NotNull Role role

) { }
