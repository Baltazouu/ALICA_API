package org.alica.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignInRequestDTO(

        @Email String email,
        @NotNull String password
) { }
