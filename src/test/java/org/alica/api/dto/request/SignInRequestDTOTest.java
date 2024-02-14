package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class SignInRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void DTOWithEmailNullShouldFail() {
        SignInRequestDTO dto = SignInRequestDTO.builder()
                .password("password")
                .build();

        Set<ConstraintViolation<SignInRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void DTOWithEmailEmptyShouldFail() {
        SignInRequestDTO dto = SignInRequestDTO.builder()
                .email("")
                .password("password")
                .build();

        Set<ConstraintViolation<SignInRequestDTO>> violations = validator.validate(dto);

        //Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void DTOWithInvalidEmailFormatShouldFail() {
        SignInRequestDTO dto = SignInRequestDTO.builder()
                .email("invalid_email_format")
                .password("password")
                .build();

        Set<ConstraintViolation<SignInRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void DTOWithNullPasswordShouldFail() {
        SignInRequestDTO dto = SignInRequestDTO.builder()
                .email("email@example.com")
                .build();

        Set<ConstraintViolation<SignInRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("password", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void DTOWithAllInvalidFieldsShouldFail() {
        SignInRequestDTO dto = SignInRequestDTO.builder().build();

        Set<ConstraintViolation<SignInRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(2, violations.size());
    }

    @Test
    void DTOWithAllValidFieldsShouldPass() {
        SignInRequestDTO dto = SignInRequestDTO.builder()
                .email("email@example.com")
                .password("password")
                .build();

        Set<ConstraintViolation<SignInRequestDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(0, violations.size());
    }
}
