package org.alica.api.Dto.request;

import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

public record RequestProfileDTO(

        @NotNull String email,

        @NotNull String name,

        @NotNull String surname,

        String linkedinURL,

        String githubURL,

        String portfolioURL

) { }
