package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

class RequestEventDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }



    @Test
    void DTOWithInvalidTitleShouldFail() {
        RequestEventDTO dto = RequestEventDTO.builder()
                .imageId(UUID.randomUUID())
                .description("description")
                .date(new Date())
                .nbMaxRegistrations(10)
                .build();

        Set<ConstraintViolation<RequestEventDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("title is required", violations.iterator().next().getMessage());
    }

    // Ajoutez des tests similaires pour les autres champs (imgURL, description, date, imageURL, nbMaxRegistrations)

    @Test
    void DTOWithAllInvalidFieldsShouldFail() {
        RequestEventDTO dto = RequestEventDTO.builder().build();

        Set<ConstraintViolation<RequestEventDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(4, violations.size());
    }

    @Test
    void DTOWithAllValidFieldsShouldPass() {
        RequestEventDTO dto = RequestEventDTO.builder()
                .title("title")
                .imageId(UUID.randomUUID())
                .description("description")
                .date(new Date())
                .nbMaxRegistrations(10)
                .build();

        Set<ConstraintViolation<RequestEventDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(0, violations.size());
    }
}

