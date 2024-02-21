package org.alica.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RequestRefreshToken(
        @NotBlank
        String refreshToken
) { }
