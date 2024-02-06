package org.alica.api.Dto.request;

public record SignInRequestDTO(
        String email,
        String password
) { }
