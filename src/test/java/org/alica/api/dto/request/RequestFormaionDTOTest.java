package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RequestFormationDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    void DTOWithInvalidNameShouldFail() {
        RequestFormationDTO dto = RequestFormationDTO.builder()
                .startDate("startDate")
                .endDate("endDate")
                .company("company")
                .currentJob(true)
                .build();

        Set<ConstraintViolation<RequestFormationDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("firstName is required", violations.iterator().next().getMessage());
    }

    // Ajoutez des tests similaires pour les autres champs (startDate, endDate, company, currentJob)

    @Test
    void DTOWithAllInvalidFieldsShouldFail() {
        RequestFormationDTO dto = RequestFormationDTO.builder().build();

        Set<ConstraintViolation<RequestFormationDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(5, violations.size());
    }

    @Test
    void DTOWithAllValidFieldsShouldPass() {
        RequestFormationDTO dto = RequestFormationDTO.builder()
                .name("firstName")
                .startDate("startDate")
                .endDate("endDate")
                .company("company")
                .currentJob(true)
                .build();

        Set<ConstraintViolation<RequestFormationDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(0, violations.size());
    }
}

