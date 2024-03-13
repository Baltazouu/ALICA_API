package org.alica.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;


@Builder
@Validated
public record RequestArticleDTO(
        @NotNull(message = "title is required") String title,
        @NotNull(message = "subtitle is required") String subtitle,
        @NotNull(message = "content is required") String content,
        @NotNull(message = "imgId is required")UUID imgId
        ) { }