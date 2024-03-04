package org.alica.api.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RequestArticleDTOTest {


    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void DTOWithInvalidTitleShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( null, "subtitle", "content", "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("title is required", violations.iterator().next().getMessage());
    }

    @Test
    void DTOWithInvalidSubtitleShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( "title", null, "content", "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("subtitle is required", violations.iterator().next().getMessage());
    }

    @Test
    void DTOWithInvalidContentShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( "title", "subtitle", null, "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("content is required", violations.iterator().next().getMessage());
    }

    @Test
    void DTOWithInvalidImgURLShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( "title", "subtitle", "content", null);

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("imgURL is required", violations.iterator().next().getMessage());
    }

    @Test
    void DTOWithAllInvalidFieldsShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO(null, null, null, null);

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(4, violations.size());
    }

    @Test
    void DTOWithAllValidFieldsShouldPass() {
        RequestArticleDTO dto = new RequestArticleDTO( "title", "subtitle", "content", "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void DTOWithInvalidAlumniIdAndInvalidTitleShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( null, "subtitle", "content", "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        for (ConstraintViolation<RequestArticleDTO> violation : violations) {
            if (violation.getPropertyPath().toString().equals("alumniId")) {
                Assertions.assertEquals("alumniId is required", violation.getMessage());
            } else if (violation.getPropertyPath().toString().equals("title")) {
                Assertions.assertEquals("title is required", violation.getMessage());
            }
        }
    }

    @Test
    void DTOWithInvalidAlumniIdAndInvalidSubtitleShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( "title", null, "content", "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        for (ConstraintViolation<RequestArticleDTO> violation : violations) {
            if (violation.getPropertyPath().toString().equals("alumniId")) {
                Assertions.assertEquals("alumniId is required", violation.getMessage());
            } else if (violation.getPropertyPath().toString().equals("subtitle")) {
                Assertions.assertEquals("subtitle is required", violation.getMessage());
            }
        }
    }

    @Test
    void DTOWithInvalidAlumniIdAndInvalidContentShouldFail() {
        RequestArticleDTO dto = new RequestArticleDTO( "title", "subtitle", null, "imgURL");

        Set<ConstraintViolation<RequestArticleDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(1, violations.size());
        for (ConstraintViolation<RequestArticleDTO> violation : violations) {
            if (violation.getPropertyPath().toString().equals("alumniId")) {
                Assertions.assertEquals("alumniId is required", violation.getMessage());
            } else if (violation.getPropertyPath().toString().equals("content")) {
                Assertions.assertEquals("content is required", violation.getMessage());
            }
        }
    }



}
