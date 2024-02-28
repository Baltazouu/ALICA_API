package org.alica.api.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record RequestRefreshDTO(
        @NotNull UUID refreshToken
        ) { }
