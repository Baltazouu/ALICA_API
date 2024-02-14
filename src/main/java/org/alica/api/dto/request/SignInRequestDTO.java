package org.alica.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record SignInRequestDTO(

        @NotNull(message = "email is required") @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,
        @NotNull(message = "password is required") String password
) { }
