package org.alica.api.dto.response;

import lombok.Builder;

import java.util.UUID;


@Builder
public record ResponseImageDTO(
        UUID id,

        String name,

        String type
) {
}
