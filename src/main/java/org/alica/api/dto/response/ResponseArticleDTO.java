package org.alica.api.dto.response;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;
import java.util.UUID;

@Validated
@Builder
public record ResponseArticleDTO(
    UUID id,
    UUID alumniId,
    String title,
    String subtitle,
    String content,
    String imgURL
) { }
