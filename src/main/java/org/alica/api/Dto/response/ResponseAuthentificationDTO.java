package org.alica.api.Dto.response;


import lombok.Builder;

@Builder
public record ResponseAuthentificationDTO(
        String token,
        String type,
       // UUID id,
        String email,
        String role
) { }
