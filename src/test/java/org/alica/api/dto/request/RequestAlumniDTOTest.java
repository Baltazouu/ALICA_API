package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
class RequestAlumniDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void DTOWithInvalidEmailShouldFail() {
        RequestAlumniDTO dto = RequestAlumniDTO.builder()
                .email("invalidEmail")
                .password("password")
                .entryYear("entryYear")
                .firstName("firstName")
                .lastName("lastName")
                .linkedinURL("linkedinURL")
                .githubURL("githubURL")
                .portfolioURL("portfolioURL")
                .imageURL("imageURL")
                .build();


        Set<ConstraintViolation<RequestAlumniDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void DTOWithInvalidPasswordShouldFail() {
        RequestAlumniDTO dto = RequestAlumniDTO.builder()
                .email("email")
                .password("")
                .entryYear("entryYear")
                .firstName("firstName")
                .lastName("lastName")
                .linkedinURL("linkedinURL")
                .githubURL("githubURL")
                .portfolioURL("portfolioURL")
                .imageURL("imageURL")
                .build();

        Set<ConstraintViolation<RequestAlumniDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void DTOWithInvalidEntryYearShouldFail() {
        RequestAlumniDTO dto = RequestAlumniDTO.builder()
                .email("email")
                .password("password")
                .entryYear("")
                .firstName("firstName")
                .lastName("lastName")
                .linkedinURL("linkedinURL")
                .githubURL("githubURL")
                .portfolioURL("portfolioURL")
                .imageURL("imageURL")
                .build();

        Set<ConstraintViolation<RequestAlumniDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void DTOWithInvalidFirstNameShouldFail() {
        RequestAlumniDTO dto = RequestAlumniDTO.builder()
                .email("email")
                .password("password")
                .entryYear("entryYear")
                .firstName("")
                .lastName("lastName")
                .linkedinURL("linkedinURL")
                .githubURL("githubURL")
                .portfolioURL("portfolioURL")
                .imageURL("imageURL")
                .build();

        Set<ConstraintViolation<RequestAlumniDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void DTOWithInvalidLastNameShouldFail() {
        RequestAlumniDTO dto = RequestAlumniDTO.builder()
                .email("email")
                .password("password")
                .entryYear("entryYear")
                .firstName("firstName")
                .lastName("")
                .linkedinURL("linkedinURL")
                .githubURL("githubURL")
                .portfolioURL("portfolioURL")
                .imageURL("imageURL")
                .build();

        Set<ConstraintViolation<RequestAlumniDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void DTOWithValidDataShouldPass() {
        RequestAlumniDTO dto = RequestAlumniDTO.builder()
                .email("testmail@gmail.com")
                .password("password")
                .entryYear("entryYear")
                .firstName("firstName")
                .lastName("lastName")
                .linkedinURL("linkedinURL")
                .githubURL("githubURL")
                .portfolioURL("portfolioURL")
                .imageURL("imageURL")
                .build();

        Set<ConstraintViolation<RequestAlumniDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(0, violations.size());
    }
}
