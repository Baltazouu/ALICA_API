package org.alica.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record SignupRequestDTO(

        @NotNull(message = "email est required")
        //@Email(message = "email is not valid")
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,

        @NotNull(message = "password is required")
        @Size(min = 6, max = 20, message = "password must be between 6 and 20 characters")
        String password,

        @NotNull(message = "name is required")
        @Size(min = 3, max = 20, message = "name must be between 3 and 20 characters")
        String name,

        @NotNull(message = "surname is required")
        @Size(min = 3, max = 20, message = "surname must be between 3 and 20 characters")
        String surname

) { }
