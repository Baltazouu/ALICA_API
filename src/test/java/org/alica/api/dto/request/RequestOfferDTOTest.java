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
    void DTOWithInvalidAlumniIdShouldFail() {
        RequestOfferDTO dto = RequestOfferDTO.builder()
                .title("title")
                .description("description")
                .EContract(EContract.CDI)
                .ELevel(ELevel.JUNIOR)
                .city("city")
                .company("company")
                .jobDescription("jobDescription")
                .EStudies(EStudies.BAC_3)
                .contactEmail("contact@example.com")
                .image("image")
                .experienceRequired("experienceRequired")
                .contactNumber(123456789)
                .companyURL("http://example.com")
                .build();

        Set<ConstraintViolation<RequestOfferDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("alumniId is required", violations.iterator().next().getMessage());
    }

    @Test
    void DTOWithInvalidTitleShouldFail() {
        RequestOfferDTO dto = RequestOfferDTO.builder()
                .alumniId(UUID.randomUUID())
                .description("description")
                .EContract(EContract.CDD)
                .ELevel(ELevel.INDIFFERENT)
                .city("city")
                .company("company")
                .jobDescription("jobDescription")
                .EStudies(EStudies.BAC_2)
                .contactEmail("contact@example.com")
                .image("image")
                .experienceRequired("experienceRequired")
                .contactNumber(123456789)
                .companyURL("http://example.com")
                .build();

        Set<ConstraintViolation<RequestOfferDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("title is required", violations.iterator().next().getMessage());
    }


    @Test
    void DTOWithAllInvalidFieldsShouldFail() {
        RequestOfferDTO dto = RequestOfferDTO.builder().build();

        Set<ConstraintViolation<RequestOfferDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(13, violations.size());
    }

    @Test
    void DTOWithAllValidFieldsShouldPass() {
        RequestOfferDTO dto = RequestOfferDTO.builder()
                .alumniId(UUID.randomUUID())
                .title("title")
                .description("description")
                .EContract(EContract.ALTERNANCE)
                .ELevel(ELevel.INDIFFERENT)
                .city("city")
                .company("company")
                .jobDescription("jobDescription")
                .EStudies(EStudies.BAC_5)
                .contactEmail("contact@example.com")
                .image("image")
                .experienceRequired("experienceRequired")
                .contactNumber(123456789)
                .companyURL("http://example.com")
                .build();

        Set<ConstraintViolation<RequestOfferDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(0, violations.size());
    }
}
