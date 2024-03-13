package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.alica.api.enums.EContract;
import org.alica.api.enums.ELevel;
import org.alica.api.enums.EStudies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

class RequestOfferDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }




    @Test
    void DTOWithAllValidFieldsShouldPass() {
        RequestOfferDTO dto = RequestOfferDTO.builder()
                .title("title")
                .description("description")
                .contract(EContract.ALTERNANCE)
                .level(ELevel.INDIFFERENT)
                .city("city")
                .company("company")
                .jobDescription("jobDescription")
                .studies(EStudies.BAC_5)
                .contactEmail("contact@example.com")
                .imageId(UUID.randomUUID())
                .experienceRequired("experienceRequired")
                .contactNumber(123456789)
                .companyURL("http://example.com")
                .build();

        Set<ConstraintViolation<RequestOfferDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
    }
}
