package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class SignupRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void signUpRequestDTOWithValidFieldsShouldPass() {
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .email("test@example.com")
                .password("password123")
                .name("John")
                .surname("Doe")
                .build();

        Set<ConstraintViolation<SignupRequestDTO>> violations = validator.validate(dto);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void signUpRequestDTOWithEmailNullShouldFail() {
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .password("password123")
                .name("John")
                .surname("Doe")
                .build();

        Set<ConstraintViolation<SignupRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("email est required", violations.iterator().next().getMessage());
    }

    @Test
    void signUpRequestDTOWithInvalidEmailFormatShouldFail() {
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .email("invalid_email_format")
                .password("password123")
                .name("John")
                .surname("Doe")
                .build();

        Set<ConstraintViolation<SignupRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }


    @Test
    void signUpRequestDTOWithAllInvalidFieldsShouldFail() {
        SignupRequestDTO dto = SignupRequestDTO.builder().build();

        Set<ConstraintViolation<SignupRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(4, violations.size());
    }
}
